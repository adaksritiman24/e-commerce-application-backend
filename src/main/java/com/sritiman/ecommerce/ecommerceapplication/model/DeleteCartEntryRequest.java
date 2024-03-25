package com.sritiman.ecommerce.ecommerceapplication.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DeleteCartEntryRequest {
    @NotEmpty(message = "productId cannot null or empty")
    private Long productId;
    private String addedComments;
}
