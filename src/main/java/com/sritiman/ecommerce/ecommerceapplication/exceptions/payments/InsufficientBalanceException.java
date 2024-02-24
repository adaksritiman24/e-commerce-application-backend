package com.sritiman.ecommerce.ecommerceapplication.exceptions.payments;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
