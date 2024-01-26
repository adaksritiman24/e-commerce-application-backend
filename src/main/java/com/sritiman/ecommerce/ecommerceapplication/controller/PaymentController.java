package com.sritiman.ecommerce.ecommerceapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentAuthorizationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments/v1")
public class PaymentController {

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/authorize")
    public String authorize(@RequestBody PaymentAuthorizationRequest paymentAuthorizationRequest) throws JsonProcessingException {
        String paymentAuthorizationRequestString = new ObjectMapper().writeValueAsString(paymentAuthorizationRequest);
        LOGGER.info("Payment Authorization Request: {}", paymentAuthorizationRequestString);

        return "Done";
    }
}
