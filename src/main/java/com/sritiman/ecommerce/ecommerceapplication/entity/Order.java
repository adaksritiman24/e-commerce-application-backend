package com.sritiman.ecommerce.ecommerceapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "buzz_order")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "entries")
    @ElementCollection
    private List<CartEntry> orderEntryList = new ArrayList<>();

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToOne(targetEntity = Address.class, cascade = {CascadeType.ALL})
    private Address deliveryAddress;

    @OneToOne(targetEntity = PaymentDetails.class, cascade = {CascadeType.ALL})
    private PaymentDetails paymentDetails;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private Customer customer;
}
