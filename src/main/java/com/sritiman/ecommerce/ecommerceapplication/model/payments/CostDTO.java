package com.sritiman.ecommerce.ecommerceapplication.model.payments;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private double totalCost;
    private double shippingCost;
    private double deductions;

    @JsonIgnore
    public boolean isPaymentRequired() {
        return totalCost - deductions > 0;
    }
}
