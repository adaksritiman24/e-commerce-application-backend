package com.sritiman.ecommerce.ecommerceapplication.rules;

import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentAuthorizationRequest;

public interface PaymentAuthorizationRules {
    void apply(PaymentAuthorizationRequest paymentAuthorizationRequest,
               PaymentAuthorizationRuleEngine paymentAuthorizationRuleEngine);
}
