package com.sritiman.ecommerce.ecommerceapplication.model.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CostDTO {
    private double totalTax;
    private double subTotal;
    private double shippingCost;
}
