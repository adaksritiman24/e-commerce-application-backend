package com.sritiman.ecommerce.ecommerceapplication.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueDeclarationService {
    private final RabbitAdmin rabbitAdmin;

    public void declareRabbitExchange(Exchange exchange) {
        rabbitAdmin.declareExchange(exchange);
        log.info("Declared Exchange: {}", exchange.getName());
    }

    public void declareRabbitQueue(Exchange exchange, Queue queue, String routingKey) {
        rabbitAdmin.declareQueue(queue);
        log.info("Declared queue: {}", queue.getName());

        Binding binding = BindingBuilder.bind(queue).to(exchange)
                .with(Objects.nonNull(routingKey) ? routingKey : "").noargs();

        log.info("Declared binding between exchange: {} and queue: {}", exchange.getName(), queue.getName());
        rabbitAdmin.declareBinding(binding);
    }
}
