package com.sritiman.ecommerce.ecommerceapplication.model.giftcard;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RedeemGiftCardRequest {
    @NotEmpty
    String username;
    @NotEmpty
    String giftCardId;
}
