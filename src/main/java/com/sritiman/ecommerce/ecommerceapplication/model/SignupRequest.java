package com.sritiman.ecommerce.ecommerceapplication.model;

import com.sritiman.ecommerce.ecommerceapplication.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {
    private String name;
    private String username;
    private String email;
    private AddressRequest address;
    private String phoneNumber;
    private String password;

    @Override
    public String toString() {
        return "SignupRequest{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
