package com.sritiman.ecommerce.ecommerceapplication.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class SignupRequest {

    @NotEmpty(message = "name cannot be null or empty")
    private String name;
    @NotEmpty(message = "username cannot be null or empty")
    @Size(min = 3, max = 30, message = "username should be at least 3 characters and at most 30 characters")
    private String username;

    @NotEmpty(message = "email cannot be null or empty")
    @Email(message = "email is not valid")
    private String email;

    @NotNull(message = "address cannot be null")
    private AddressRequest address;

    @NotEmpty(message = "phoneNumber cannot be null or empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number should be of 10 digits")
    private String phoneNumber;

    @NotEmpty(message = "password cannot be null or empty")
    @Size(min = 8, message = "password must be of at least 8 characters")
    private String password;

    @NotNull(message = "anonymousCartUsername field cannot be null")
    private String anonymousCartUsername;

}
