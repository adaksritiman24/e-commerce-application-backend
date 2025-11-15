package com.sritiman.ecommerce.ecommerceapplication.model.giftcard;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GetGiftCardsResponse {
    String id;
    BigDecimal amount;
    String title;
    String description;
    String issuer;
}
