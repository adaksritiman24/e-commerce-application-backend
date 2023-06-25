package com.sritiman.ecommerce.ecommerceapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateCartRequest {
    private Long productId;
    private int quantity;
}
