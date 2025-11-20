package com.sritiman.ecommerce.ecommerceapplication.rules;

import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentAuthorizationRequest;

public class GiftCardPaymentAuthorizationRule implements PaymentAuthorizationRules{
    @Override
    public void apply(PaymentAuthorizationRequest paymentAuthorizationRequest,
                      PaymentAuthorizationRuleEngine paymentAuthorizationRuleEngine) {

        if(Boolean.TRUE.equals(paymentAuthorizationRequest.getIsGiftCardApplied())) {
            paymentAuthorizationRequest.getCost().setDeductions(
                    paymentAuthorizationRequest.getGiftCard().getAmount()
            );
        }
        paymentAuthorizationRuleEngine.applyAll(paymentAuthorizationRequest);
    }
}
