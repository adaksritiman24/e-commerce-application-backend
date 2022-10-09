package com.sritiman.ecommerce.ecommerceapplication.exceptions;

public class CustomerSignupDatabaseException extends RuntimeException{
    private String message;

    public CustomerSignupDatabaseException(String message) {
        super(message);
        this.message = message;
    }

}
