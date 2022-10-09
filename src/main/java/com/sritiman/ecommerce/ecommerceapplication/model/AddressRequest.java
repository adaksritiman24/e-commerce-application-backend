package com.sritiman.ecommerce.ecommerceapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@AllArgsConstructor
@Getter
@Setter
public class AddressRequest {
    private String house;
    private String locality;
    private String city;
    private String country;
    private String pincode;

    @Override
    public String toString() {
        return "AddressRequest{" +
                "house='" + house + '\'' +
                ", locality='" + locality + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", pincode='" + pincode + '\'' +
                '}';
    }
}
