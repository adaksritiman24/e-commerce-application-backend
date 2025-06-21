package com.sritiman.ecommerce.ecommerceapplication.model.response;

import com.sritiman.ecommerce.ecommerceapplication.entity.OrderStatus;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderHistoryItemDTO {
    long id;
    Date orderDate;
    Double totalPrice;
    private OrderStatus status;
}
