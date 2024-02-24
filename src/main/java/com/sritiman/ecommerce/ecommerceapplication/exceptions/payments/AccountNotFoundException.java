package com.sritiman.ecommerce.ecommerceapplication.exceptions.payments;

import lombok.Getter;

@Getter
public class AccountNotFoundException extends RuntimeException{
    private String details;
    public AccountNotFoundException(String message, String details) {
        super(message);
        this.details = details;
    }
}
