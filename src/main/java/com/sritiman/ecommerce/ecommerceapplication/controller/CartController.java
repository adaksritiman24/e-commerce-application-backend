package com.sritiman.ecommerce.ecommerceapplication.controller;

import com.sritiman.ecommerce.ecommerceapplication.entity.Cart;
import com.sritiman.ecommerce.ecommerceapplication.model.DeleteCartEntryRequest;
import com.sritiman.ecommerce.ecommerceapplication.model.UpdateCartRequest;
import com.sritiman.ecommerce.ecommerceapplication.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<Cart> getCart(@PathVariable String username){
        try {
            return new ResponseEntity<>(cartService.getCartForCustomer(username), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/anonymous/{customerId}")
    public ResponseEntity<Cart> getAnonymousCart(@PathVariable String customerId){
        try {
            return new ResponseEntity<>(cartService.getAnonymousCart(customerId), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/{username}")
    public ResponseEntity<Cart> updateCart(@PathVariable String username,
                                        @RequestBody UpdateCartRequest updateCartRequest){
        try {
            return new ResponseEntity<>(cartService.updateCart(username, updateCartRequest), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{username}/remove")
    public ResponseEntity<Cart> deleteCartEntry(@PathVariable String username,
                                                @RequestBody DeleteCartEntryRequest deleteCartEntryRequest) {
        try {
            return new ResponseEntity<>(cartService.removeCartEntry(username, deleteCartEntryRequest), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Deprecated
    @PostMapping("/{username}/merge")
    public ResponseEntity<Cart> mergeCart(@PathVariable String username,
                                          @RequestBody List<UpdateCartRequest> fromCartItems) {
        try {
            return new ResponseEntity<>(cartService.mergeCart(username, fromCartItems), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
