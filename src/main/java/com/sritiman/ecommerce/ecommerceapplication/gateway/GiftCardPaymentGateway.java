package com.sritiman.ecommerce.ecommerceapplication.gateway;

import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentAuthorizationRequest;
import org.springframework.stereotype.Service;

@Service
public class GiftCardPaymentGateway implements PaymentGateway{
    @Override
    public String capture(PaymentAuthorizationRequest paymentAuthorizationRequest) {
        return null;
    }
}
