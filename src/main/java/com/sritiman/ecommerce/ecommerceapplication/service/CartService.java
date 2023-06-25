package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.Cart;
import com.sritiman.ecommerce.ecommerceapplication.entity.CartEntry;
import com.sritiman.ecommerce.ecommerceapplication.entity.Customer;
import com.sritiman.ecommerce.ecommerceapplication.entity.Product;
import com.sritiman.ecommerce.ecommerceapplication.model.DeleteCartEntryRequest;
import com.sritiman.ecommerce.ecommerceapplication.model.UpdateCartRequest;
import com.sritiman.ecommerce.ecommerceapplication.repository.CartRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.CustomerRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Cart getCartForCustomer(String username) throws Exception {
        Customer customer = customerRepository.findByUsername(username);
        LOG.info("Customer: {}", customer);
        if (Objects.nonNull(customer)) {
            return customer.getCart();
        }
        throw new Exception("Customer not found: " + username);
    }

    public Cart updateCart(String username, UpdateCartRequest updateCartRequest) throws Exception {
        Cart customerCart = getCartForCustomer(username);
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
        List<CartEntry> toCartItems = cart.getCartEntryList();
        if(Objects.isNull(fromCartItems) || fromCartItems.isEmpty()) {
            return cart;
        }
        List<CartEntry> newItems = new ArrayList<>();
        for(UpdateCartRequest fromCartEntry : fromCartItems) {
            boolean cartEntryFound = false;
            for (CartEntry toCartEntry: toCartItems) {
                if(fromCartEntry.getProductId().equals(toCartEntry.getProductId())) { //if match found , update quantity
                    cartEntryFound = true;
                    toCartEntry.setQuantity(toCartEntry.getQuantity() + fromCartEntry.getQuantity());
                }
            }

            if(!cartEntryFound) { //populate new Items if not found in cart
                newItems.add(createNewCartEntry(fromCartEntry.getProductId(), fromCartEntry.getQuantity()));
            }
        }
        //Add the newItems to cart entry list
        toCartItems.addAll(newItems);
        cart.setCartEntryList(toCartItems);
        cartRepository.save(cart);
        return getCartForCustomer(username);

    }

    private CartEntry createNewCartEntry(Long productId, int quantity) {
        return new CartEntry(productId, quantity);
    }

    private boolean isValidProductId(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.isPresent();
    }


}
