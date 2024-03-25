package com.sritiman.ecommerce.ecommerceapplication.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddDeliveryAddressRequest {

    @NotEmpty(message = "house cannot be null or empty")
    private String house;

    @NotEmpty(message = "locality cannot be null or empty")
    private String locality;

    @NotEmpty(message = "city cannot be null or empty")
    private String city;

    @NotEmpty(message = "country cannot be null or empty")
    private String country;

    @NotEmpty(message = "pincode cannot be null or empty")
    private String pincode;

    @NotEmpty(message = "phone cannot be null or empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number should be of 10 digits")
    private String phone;

    @NotEmpty(message = "email cannot be null or empty")
    @Email(message = "not a valid email id")
    private String email;

    @NotEmpty(message = "name cannot be null or empty")
    @Size(min = 5, max = 30, message = "name should be at least 5 characters, at most 30 characters")
    private String name;
}
