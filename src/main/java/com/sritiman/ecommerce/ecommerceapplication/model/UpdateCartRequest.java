package com.sritiman.ecommerce.ecommerceapplication.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateCartRequest {

    private Long productId;

    @Max(value = 100, message = "Quantity should be within 100")
    private int quantity;
}
