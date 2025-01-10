package com.sritiman.ecommerce.ecommerceapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentAuthorizationRequest;
import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentResponseDTO;
import com.sritiman.ecommerce.ecommerceapplication.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@CrossOrigin
public class PaymentController {

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/v1/capture")
    public ResponseEntity<PaymentResponseDTO> authorize(@RequestBody PaymentAuthorizationRequest paymentAuthorizationRequest) throws JsonProcessingException {
        PaymentResponseDTO paymentResponseDTO = paymentService.capturePayment(paymentAuthorizationRequest);
        LOGGER.info("Payment Request: {} ", paymentResponseDTO);
        return new ResponseEntity<>(paymentResponseDTO, HttpStatus.OK);
    }
}
