package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.*;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CartNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.model.DeleteCartEntryRequest;
import com.sritiman.ecommerce.ecommerceapplication.model.UpdateCartRequest;
import com.sritiman.ecommerce.ecommerceapplication.repository.CartRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.CustomerRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
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

    public Cart getAnonymousCart(String anonymousCustomerId) {
        Customer anonymousCustomer = customerRepository.findByUsername(anonymousCustomerId);
        if(Objects.isNull(anonymousCustomer)) {
            //create a new anonymous customer in db and attach it to an anonymous cart
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
            Cart customerCart = customer.getCart();
            calculateCart(customerCart.getId());
            return customerCart;
        }
        throw new Exception("Customer not found: " + username);
    }

    public Cart updateCart(String username, UpdateCartRequest updateCartRequest) throws Exception {
        Cart customerCart = getCartForCustomer(username);
        populateDeliveryAddress(customerCart, customerRepository.findByUsername(username));
        List<CartEntry> cartEntryList = customerCart.getCartEntryList();
        if (!isValidProductId(updateCartRequest.getProductId())) {
            LOG.info("Product not found for cart: {}", customerCart.getId());
            throw new Exception("Product not found!");
        }

        if (cartEntryList.isEmpty()) { //empty cart -> add new entry
            if (updateCartRequest.getQuantity() > 0) {
                cartEntryList.add(createNewCartEntry(updateCartRequest.getProductId(), updateCartRequest.getQuantity()));
                customerCart.setCartEntryList(cartEntryList);
            }
        } else { //cart has items
            //find existing entry
            Optional<CartEntry> optionalCartEntry = cartEntryList.stream()
                    .filter(cartEntry -> cartEntry.getProductId() == updateCartRequest.getProductId()).findFirst();
            if (optionalCartEntry.isPresent()) { //product already present in cart
                List<CartEntry> updatedCartEntryList = cartEntryList.stream().map(cartEntry -> {
                    if (cartEntry.getProductId() == updateCartRequest.getProductId()) {
                        cartEntry.setQuantity(Math.max(cartEntry.getQuantity() + updateCartRequest.getQuantity(), 1));
                    }
                    return cartEntry;
                }).collect(Collectors.toList());
                customerCart.setCartEntryList(updatedCartEntryList);
            } else { //product not present in cart -> add new entry
                if (updateCartRequest.getQuantity() > 0) {
                    cartEntryList.add(createNewCartEntry(updateCartRequest.getProductId(), updateCartRequest.getQuantity()));
                    customerCart.setCartEntryList(cartEntryList);
                }
            }
        }
        cartRepository.save(customerCart);
        return getCartForCustomer(username);
    }

    private void populateDeliveryAddress(Cart customerCart, Customer customer) {
        if(Objects.isNull(customerCart.getDeliveryAddress()) && Objects.nonNull(customer) && Objects.nonNull(customer.getAddress())) {
            Address deliveryAddress = createDeliveryAddress(customer.getAddress());
            deliveryAddress.setPhone(customer.getPhoneNumber());
            deliveryAddress.setEmail(customer.getEmail());
            customerCart.setDeliveryAddress(deliveryAddress);
        }
    }

    public Cart removeCartEntry(String username, DeleteCartEntryRequest deleteCartEntryRequest) throws Exception {
        Long productId = deleteCartEntryRequest.getProductId();
        Cart cart = getCartForCustomer(username);
        List<CartEntry> updatedCartEntries = cart.getCartEntryList().stream().filter(cartEntry -> cartEntry.getProductId() != productId).collect(Collectors.toList());
        cart.setCartEntryList(updatedCartEntries);
        cartRepository.save(cart);

        return getCartForCustomer(username);
    }

    public Cart mergeCart(String username, List<UpdateCartRequest> fromCartItems) throws Exception {
        Cart cart = getCartForCustomer(username);
        populateDeliveryAddress(cart, customerRepository.findByUsername(username));
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
        cartRepository.save(cart);
        return getCartForCustomer(username);

    }

    private Address createDeliveryAddress(Address address) {
        Address deliveryAddress = new Address();
        deliveryAddress.setCity(address.getCity());
        deliveryAddress.setHouse(address.getHouse());
        deliveryAddress.setCountry(address.getCountry());
        deliveryAddress.setLocality(address.getLocality());
        deliveryAddress.setPincode(address.getPincode());
        return  deliveryAddress;
    }

    private CartEntry createNewCartEntry(Long productId, int quantity) {
        return new CartEntry(productId, quantity, 0.0, 0.0);
    }

    private boolean isValidProductId(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.isPresent();
    }

    private void calculateCart(long cartID) {
        Optional<Cart> cartOPT = cartRepository.findById(cartID);
        if (cartOPT.isEmpty()) {
            LOG.info("Cart with id : {} does not exist!", cartID);
            throw new CartNotFoundException("Cart not found: " + cartID);
        }
        Cart cart = cartOPT.get();
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        for (CartEntry cartEntry : cart.getCartEntryList()) {
            Optional<Product> productOPT = productRepository.findById(cartEntry.getProductId());
            if (productOPT.isPresent()) {
                Product product = productOPT.get();
                BigDecimal discountedPrice = BigDecimal.valueOf(product.getDiscountedPrice());
                BigDecimal quantity = BigDecimal.valueOf(cartEntry.getQuantity());
                cartEntry.setUnitPrice(discountedPrice.setScale(2, RoundingMode.HALF_EVEN).doubleValue());
                cartEntry.setTotalPrice(discountedPrice.multiply(quantity).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
                cartTotalPrice = cartTotalPrice.add(BigDecimal.valueOf(cartEntry.getTotalPrice()));
            }
        }
        cart.setTotalPrice(cartTotalPrice.setScale(2, RoundingMode.HALF_EVEN).doubleValue());
        cartRepository.save(cart);
    }
}
