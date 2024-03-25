package com.sritiman.ecommerce.ecommerceapplication.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DeleteCartEntryRequest {
    private Long productId;
    private String addedComments;
}
