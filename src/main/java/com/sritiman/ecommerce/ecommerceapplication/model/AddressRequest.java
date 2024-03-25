package com.sritiman.ecommerce.ecommerceapplication.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddressRequest {
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
}
