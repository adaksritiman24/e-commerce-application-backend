package com.sritiman.ecommerce.ecommerceapplication.config;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@AllArgsConstructor
@Slf4j
public class QueueConfiguration {
    private final MessagingConfig messagingConfig;
    private final QueueDeclarationService queueDeclarationService;

    @PostConstruct
    public void declareAMQP() {
        log.info("Declaring Required Queues and Exchanges");
        messagingConfig.getExchangeInformationList()
                .forEach(this::declareExchangesAndQueues);
    }

    private Exchange createExchange(String exchangeName, String exchangeType) {
        return switch (exchangeType) {
            case "fanout" -> ExchangeBuilder.fanoutExchange(exchangeName).durable(true).build();
            case "topic" -> ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
            default -> ExchangeBuilder.directExchange(exchangeName).durable(true).build();
        };
    }

    private void createQueue(MessagingConfig.QueueInformation queueInformation, Exchange exchange) {
        Queue queue = QueueBuilder.durable(queueInformation.getName()).build();
        queueDeclarationService.declareRabbitQueue(exchange, queue, queueInformation.getRoutingKey());
    }

    private void declareExchangesAndQueues(MessagingConfig.ExchangeInformation exchangeInformation) {
        Exchange exchange = createExchange(exchangeInformation.getName(), exchangeInformation.getType());
        queueDeclarationService.declareRabbitExchange(exchange);
        exchangeInformation.getQueues().stream()
                .filter(Objects::nonNull)
                .forEach(queueInformation -> createQueue(queueInformation, exchange));
    }
}
