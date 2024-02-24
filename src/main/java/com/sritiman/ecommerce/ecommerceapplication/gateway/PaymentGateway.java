package com.sritiman.ecommerce.ecommerceapplication.gateway;

import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentAuthorizationRequest;

public interface PaymentGateway {
    String capture(PaymentAuthorizationRequest paymentAuthorizationRequest);
}
