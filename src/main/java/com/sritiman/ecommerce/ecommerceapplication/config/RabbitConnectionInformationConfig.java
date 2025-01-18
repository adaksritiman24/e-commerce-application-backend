package com.sritiman.ecommerce.ecommerceapplication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "messaging.connection-information")
@Data
public class RabbitConnectionInformationConfig {
    String host;
    int port;
    String username;
    String password;
}
