package com.sritiman.ecommerce.ecommerceapplication.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;

@Embeddable
@NoArgsConstructor
@Getter
public class Review {

    @OneToOne(targetEntity = Customer.class, cascade = CascadeType.ALL)
    Customer customer;

    int rating;

    String text;
}
