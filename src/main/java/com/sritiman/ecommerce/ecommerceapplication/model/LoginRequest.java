package com.sritiman.ecommerce.ecommerceapplication.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginRequest {
    @NotEmpty(message = "username cannot be null or empty")
    private String username;

    @NotEmpty(message = "password cannot be null or empty")
    private String password;


    private String anonymousCartUsername;
}
