package com.sritiman.ecommerce.ecommerceapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "payment_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name= "payment_mode")
    private PaymentMode paymentMode;
}
