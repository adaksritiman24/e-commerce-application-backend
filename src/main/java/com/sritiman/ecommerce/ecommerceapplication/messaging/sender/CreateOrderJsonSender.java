package com.sritiman.ecommerce.ecommerceapplication.messaging.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sritiman.ecommerce.ecommerceapplication.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreateOrderJsonSender {
    private static final String ORDER_CREATE_EXCHANGE = "Buzz.Order.Create.Exchange";
    private static final ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
    }
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(Order order) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(ORDER_CREATE_EXCHANGE, "", objectMapper.writeValueAsString(order));
        log.info("Sent Order JSON: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(order));
    }
}
