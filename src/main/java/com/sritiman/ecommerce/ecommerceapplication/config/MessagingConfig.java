package com.sritiman.ecommerce.ecommerceapplication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Configuration
@ConfigurationProperties(prefix = "messaging")
@Data
public class MessagingConfig {
    private Map<String, ExchangeInformation> exchangeInformation;

    @Data
    public static class QueueInformation {
        private String name;
        private String routingKey;
    }

    @Data
    public static class ExchangeInformation {
        private String name;
        private String type;
        private List<QueueInformation> queues = new ArrayList<>();
    }

    public List<ExchangeInformation> getExchangeInformationList() {
        if(Objects.isNull(exchangeInformation)) {
            return new ArrayList<>();
        }
        return exchangeInformation.values().stream().toList();
    }
}
