package com.sritiman.ecommerce.ecommerceapplication.model;

import com.sritiman.ecommerce.ecommerceapplication.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private Customer customer;
    private String authToken;

    @Override
    public String toString() {
        return "SignupResponse{" +
                "customer=" + customer +
                '}';
    }
}
