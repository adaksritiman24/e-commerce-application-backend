package com.sritiman.ecommerce.ecommerceapplication.model;

import com.sritiman.ecommerce.ecommerceapplication.entity.Address;
import com.sritiman.ecommerce.ecommerceapplication.entity.Customer;
import com.sritiman.ecommerce.ecommerceapplication.entity.PaymentDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {
    private long id;
    private List<OrderEntryDTO> orderEntryList;
    private Double totalPrice;
    private Date orderDate;
    private Address deliveryAddress;
    private PaymentDetails paymentDetails;
    private Customer customer;
}
