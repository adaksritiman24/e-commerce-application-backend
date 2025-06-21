package com.sritiman.ecommerce.ecommerceapplication.controller;

import com.sritiman.ecommerce.ecommerceapplication.model.GetOrdersRequestDTO;
import com.sritiman.ecommerce.ecommerceapplication.model.OrderDTO;
import com.sritiman.ecommerce.ecommerceapplication.model.response.OrderHistoryResponseDTO;
import com.sritiman.ecommerce.ecommerceapplication.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    public ResponseEntity<OrderHistoryResponseDTO> getOrders(@RequestBody GetOrdersRequestDTO getOrdersRequestDTO){
        return new ResponseEntity<>(orderService.getAllOrders(getOrdersRequestDTO.getUsername(), getOrdersRequestDTO.getPageNumber()),HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable(name = "orderId") Long order) {
        return new ResponseEntity<>(orderService.getOrder(order), HttpStatus.OK);
    }
}
