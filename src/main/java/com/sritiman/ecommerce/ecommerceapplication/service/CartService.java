package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.*;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CartNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomerNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.model.AddDeliveryAddressRequest;
import com.sritiman.ecommerce.ecommerceapplication.model.DeleteCartEntryRequest;
import com.sritiman.ecommerce.ecommerceapplication.model.UpdateCartRequest;
import com.sritiman.ecommerce.ecommerceapplication.repository.CartRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.CustomerRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final Logger LOG = LoggerFactory.getLogger(CartService.class);

    @Autowired
    public CartService(CustomerRepository customerRepository, CartRepository cartRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Cart getAnonymousCart(String anonymousCustomerId) {
        Customer anonymousCustomer = customerRepository.findByUsername(anonymousCustomerId);
        if (Objects.isNull(anonymousCustomer)) {
            //create a new anonymous customer in db and attach it to a cart
            Customer customer = new Customer();
            customer.setUsername(anonymousCustomerId);
            customer.setCart(new Cart());
            customerRepository.save(customer);
            return customer.getCart();
        }
        Cart anonymousCustomerCart = anonymousCustomer.getCart();
        calculateCart(anonymousCustomerCart.getId());
        return anonymousCustomerCart;
    }

    public Cart getCartForCustomer(String username) throws Exception {
        Customer customer = customerRepository.findByUsername(username);
        LOG.info("Customer: {}", customer);
        if (Objects.nonNull(customer)) {
            Cart cart = customer.getCart();
            calculateCart(cart.getId());
            return cart;
        }
        throw new Exception("Customer not found: " + username);
    }

    public Cart updateCart(String username, UpdateCartRequest updateCartRequest) throws Exception {
        Cart customerCart = getCartForCustomer(username);
        populateDeliveryAddressToCustomerCart(customerRepository.findByUsername(username));
        List<CartEntry> cartEntryList = customerCart.getCartEntryList();

        validateProductId(updateCartRequest, customerCart);
        updateCartEntries(updateCartRequest, cartEntryList, customerCart);

        cartRepository.save(customerCart);
        return getCartForCustomer(username);
    }

    private void updateCartEntries(UpdateCartRequest updateCartRequest,
                                   List<CartEntry> cartEntryList,
                                   Cart customerCart) {

        CartEntry cartEntry = cartEntryList.stream()
                .filter(entry -> entry.getProductId() == updateCartRequest.getProductId())
                .findFirst()
                .orElse(null);

        if (Objects.isNull(cartEntry) && updateCartRequest.getQuantity() > 0) {
            cartEntryList.add(
                    createNewCartEntry(updateCartRequest.getProductId(), updateCartRequest.getQuantity())
            );
            customerCart.setCartEntryList(cartEntryList);
        }
        if (Objects.nonNull(cartEntry)) {
            List<CartEntry> updatedCartEntryList = cartEntryList.stream()
                    .map(getUpdatedCartEntryWithMatchingProductId(updateCartRequest))
                    .collect(Collectors.toList());

            customerCart.setCartEntryList(updatedCartEntryList);
        }
    }

    private Function<CartEntry, CartEntry> getUpdatedCartEntryWithMatchingProductId(UpdateCartRequest updateCartRequest) {
        return entry -> {
            if (entry.getProductId() == updateCartRequest.getProductId()) {
                entry.setQuantity(Math.max(entry.getQuantity() + updateCartRequest.getQuantity(), 1));
            }
            return entry;
        };
    }

    private void validateProductId(UpdateCartRequest updateCartRequest, Cart customerCart) throws Exception {
        if (!isValidProductId(updateCartRequest.getProductId())) {
            LOG.info("Product not found for cart: {}", customerCart.getId());
            throw new Exception("Product not found!");
        }
    }

    private void populateDeliveryAddressToCustomerCart(Customer customer) {
        Cart customerCart = customer.getCart();
        if (Objects.isNull(customerCart.getDeliveryAddress()) &&
                Objects.nonNull(customer.getAddress())) {

            Address deliveryAddress = createDeliveryAddress(customer.getAddress());
            deliveryAddress.setPhone(customer.getPhoneNumber());
            deliveryAddress.setEmail(customer.getEmail());
            deliveryAddress.setName(customer.getName());
            customerCart.setDeliveryAddress(deliveryAddress);
        }
    }

    @Transactional
    public Cart removeCartEntry(String username, DeleteCartEntryRequest deleteCartEntryRequest) throws Exception {
        Long productId = deleteCartEntryRequest.getProductId();
        Cart cart = getCartForCustomer(username);
        List<CartEntry> updatedCartEntries = cart.getCartEntryList()
                .stream()
                .filter(cartEntry -> cartEntry.getProductId() != productId)
                .collect(Collectors.toList());

        cart.setCartEntryList(updatedCartEntries);
        cartRepository.save(cart);

        return getCartForCustomer(username);
    }

    @Transactional
    public Cart mergeCustomerCart(String username, List<UpdateCartRequest> fromCartItems) {
        Customer customer = customerRepository.findByUsername(username);
        if (Objects.isNull(customer)) {
            throw new CustomerNotFoundException("Customer Not Found");
        }
        return mergeCart(customer, fromCartItems);
    }

    public Cart mergeCart(Customer customer, List<UpdateCartRequest> fromCartItems) {
        Cart cart = customer.getCart();
        populateDeliveryAddressToCustomerCart(customer);
        List<CartEntry> toCartItems = cart.getCartEntryList();
        if (Objects.isNull(fromCartItems) || fromCartItems.isEmpty()) {
            return cart;
        }
        List<CartEntry> newItems = new ArrayList<>();
        for (UpdateCartRequest fromCartEntry : fromCartItems) {
            boolean cartEntryFound = false;
            for (CartEntry toCartEntry : toCartItems) {
                if (fromCartEntry.getProductId().equals(toCartEntry.getProductId())) { //if match found , update quantity
                    cartEntryFound = true;
                    toCartEntry.setQuantity(toCartEntry.getQuantity() + fromCartEntry.getQuantity());
                }
            }
            if (!cartEntryFound) { //populate new Items if not found in cart
                newItems.add(createNewCartEntry(fromCartEntry.getProductId(), fromCartEntry.getQuantity()));
            }
        }
        //Add the newItems to cart entry list
        toCartItems.addAll(newItems);
        cart.setCartEntryList(toCartItems);
        return cartRepository.save(cart);
    }

    private Address createDeliveryAddress(Address address) {
        Address deliveryAddress = new Address();
        deliveryAddress.setCity(address.getCity());
        deliveryAddress.setHouse(address.getHouse());
        deliveryAddress.setCountry(address.getCountry());
        deliveryAddress.setLocality(address.getLocality());
        deliveryAddress.setPincode(address.getPincode());
        return deliveryAddress;
    }

    private CartEntry createNewCartEntry(Long productId, int quantity) {
        return new CartEntry(productId, quantity, 0.0, 0.0);
    }

    private boolean isValidProductId(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.isPresent();
    }

    private void calculateCart(long cartId) {
        Optional<Cart> cartOPT = cartRepository.findById(cartId);
        if (cartOPT.isEmpty()) {
            LOG.info("Cart with id : {} does not exist!", cartId);
            throw new CartNotFoundException("Cart not found: " + cartId);
        }
        Cart cart = cartOPT.get();
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        for (CartEntry cartEntry : cart.getCartEntryList()) {
            Optional<Product> productOPT = productRepository.findById(cartEntry.getProductId());
            if (productOPT.isPresent()) {
                calculateCartEntry(cartEntry, productOPT.get());
                cartTotalPrice = cartTotalPrice.add(BigDecimal.valueOf(cartEntry.getTotalPrice()));
            }
        }
        cart.setTotalPrice(cartTotalPrice.setScale(2, RoundingMode.HALF_EVEN).doubleValue());
        cartRepository.save(cart);
    }

    private void calculateCartEntry(CartEntry cartEntry, Product product) {
        BigDecimal discountedPrice = BigDecimal.valueOf(product.getDiscountedPrice());
        BigDecimal quantity = BigDecimal.valueOf(cartEntry.getQuantity());
        cartEntry.setUnitPrice(discountedPrice.setScale(2,
                RoundingMode.HALF_EVEN).doubleValue());
        cartEntry.setTotalPrice(discountedPrice.multiply(quantity)
                .setScale(2, RoundingMode.HALF_EVEN).doubleValue());
    }

    public String addDeliveryAddress(AddDeliveryAddressRequest deliveryAddressRequest, String username) {
        Customer customer = customerRepository.findByUsername(username);
        if (Objects.isNull(customer)) {
            return "failed";
        }
        Address address = createDeliveryAddress(deliveryAddressRequest);

        customer.getCart().setDeliveryAddress(address);
        customer.setAddress(address);
        if (Objects.nonNull(deliveryAddressRequest.getPhone())) {
            customer.setPhoneNumber(deliveryAddressRequest.getPhone());
        }
        customerRepository.save(customer);
        return "success";
    }

    private static Address createDeliveryAddress(AddDeliveryAddressRequest deliveryAddressRequest) {
        return Address.builder()
                .name(deliveryAddressRequest.getName())
                .country(deliveryAddressRequest.getCountry())
                .city(deliveryAddressRequest.getCity())
                .house(deliveryAddressRequest.getHouse())
                .locality(deliveryAddressRequest.getLocality())
                .pincode(deliveryAddressRequest.getPincode())
                .email(deliveryAddressRequest.getEmail())
                .phone(deliveryAddressRequest.getPhone())
                .build();
    }
}
