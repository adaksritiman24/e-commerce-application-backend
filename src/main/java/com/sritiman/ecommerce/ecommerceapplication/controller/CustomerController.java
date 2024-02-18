package com.sritiman.ecommerce.ecommerceapplication.controller;

import com.sritiman.ecommerce.ecommerceapplication.entity.Customer;
import com.sritiman.ecommerce.ecommerceapplication.model.*;
import com.sritiman.ecommerce.ecommerceapplication.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

    private final CustomerService customerService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> getCustomerDetails(@RequestBody SignupRequest signupRequest) {
        SignupResponse signupResponse = customerService.signup(signupRequest);
        logger.info("New customer created: {}", signupResponse);
        return new ResponseEntity<>(signupResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = customerService.login(loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getAnonymousCartUsername());
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/me")
    public ResponseEntity<Customer> validateTokenAndReturnCustomerData(@RequestBody TokenValidationRequest tokenValidationRequest) {
        return new ResponseEntity<>(
                customerService.getMe(tokenValidationRequest.getToken()),
                HttpStatus.OK
        );
    }


}
