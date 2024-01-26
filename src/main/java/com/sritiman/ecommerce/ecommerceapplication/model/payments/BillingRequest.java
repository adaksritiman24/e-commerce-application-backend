package com.sritiman.ecommerce.ecommerceapplication.model.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillingRequest {
    private String name;
    private String address;
    private String city;
    private String country;
    private String zipcode;
}
