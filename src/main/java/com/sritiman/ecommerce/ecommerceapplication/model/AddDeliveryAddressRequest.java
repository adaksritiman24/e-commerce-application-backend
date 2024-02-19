package com.sritiman.ecommerce.ecommerceapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddDeliveryAddressRequest {
    private String house;
    private String locality;
    private String city;
    private String country;
    private String pincode;
    private String phone;
    private String email;
    private String name;
}
