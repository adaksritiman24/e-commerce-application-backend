package com.sritiman.ecommerce.ecommerceapplication.client;

import com.sritiman.ecommerce.ecommerceapplication.client.configs.PaymentsConfiguration;
import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentAuthorizationRequest;
import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.sritiman.ecommerce.ecommerceapplication.constants.Constants.PAYMENTS_SERVICE;

@FeignClient(name = PAYMENTS_SERVICE, url = "${feign.client.config.payment-service.url}", configuration = PaymentsConfiguration.class)
public interface PaymentsClient {
    @PostMapping("/payments/v1/capture")
    PaymentResponseDTO authorizeCapture(@RequestBody PaymentAuthorizationRequest paymentAuthorizationRequest);
}
