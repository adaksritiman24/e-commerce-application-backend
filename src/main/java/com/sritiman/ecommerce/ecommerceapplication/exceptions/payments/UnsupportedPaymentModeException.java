package com.sritiman.ecommerce.ecommerceapplication.exceptions.payments;

public class UnsupportedPaymentModeException extends RuntimeException{
    public UnsupportedPaymentModeException(String message) {
        super(message);
    }
}
