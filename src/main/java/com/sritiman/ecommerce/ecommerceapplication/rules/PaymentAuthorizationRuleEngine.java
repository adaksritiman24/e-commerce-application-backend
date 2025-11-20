package com.sritiman.ecommerce.ecommerceapplication.rules;

import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentAuthorizationRequest;

import java.util.List;

public class PaymentAuthorizationRuleEngine {
    private final List<PaymentAuthorizationRules> paymentAuthorizationRules;
    private int currentIndex = 0;

    public PaymentAuthorizationRuleEngine(List<PaymentAuthorizationRules> paymentAuthorizationRules) {
        this.paymentAuthorizationRules = paymentAuthorizationRules;
    }

    public void applyAll(PaymentAuthorizationRequest paymentAuthorizationRequest) {
        if (currentIndex < paymentAuthorizationRules.size()) {
            PaymentAuthorizationRules currentAuthorizationRule =
                    this.paymentAuthorizationRules.get(currentIndex++);
            currentAuthorizationRule.apply(paymentAuthorizationRequest, this);
        }
    }
}
