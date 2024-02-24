package com.sritiman.ecommerce.ecommerceapplication.model.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BankCardDTO {
    private String cardNumber;
    private String cvv;
    private String name;
    private String expDate;
}
