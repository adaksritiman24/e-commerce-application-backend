package com.sritiman.ecommerce.ecommerceapplication.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenValidationRequest {
    @NotEmpty(message = "field: token cannot be null or empty")
    private String token;
}
