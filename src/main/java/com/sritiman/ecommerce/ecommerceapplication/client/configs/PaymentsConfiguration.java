package com.sritiman.ecommerce.ecommerceapplication.client.configs;

import com.sritiman.ecommerce.ecommerceapplication.client.error_decoders.PaymentsErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PaymentsConfiguration {

    @Bean
    public ErrorDecoder customPaymentsErrorDecoder() {
        return new PaymentsErrorDecoder();
    }
}
