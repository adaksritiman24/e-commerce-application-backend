package com.sritiman.ecommerce.ecommerceapplication.model.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentAuthorizationRequest {

    @JsonProperty(required = true)
    private PaymentModeDTO paymentMode;

    @JsonProperty(required = true)
    private String customerId;

    private BankCardDTO bankCard;
    private GiftCardDTO giftCard;

    @JsonProperty(required = true)
    private CostDTO cost;
}
