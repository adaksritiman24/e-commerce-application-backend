package com.sritiman.ecommerce.ecommerceapplication.exceptions;

public class CustomNotFoundException extends RuntimeException{
    public CustomNotFoundException(String message) {
        super(message);
    }
}
