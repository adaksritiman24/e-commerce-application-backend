package com.sritiman.ecommerce.ecommerceapplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("")
    public String getCustomerDetails() {
        return "Sritiman Adak";
    }
}
