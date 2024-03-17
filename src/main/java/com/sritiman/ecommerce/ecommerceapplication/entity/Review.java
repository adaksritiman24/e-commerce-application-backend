package com.sritiman.ecommerce.ecommerceapplication.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Embeddable
@NoArgsConstructor
@Getter
public class Review {

    @OneToOne(targetEntity = Customer.class, cascade = CascadeType.ALL)
    Customer customer;

    int rating;

    String text;

    @Column(name = "date")
    @CreationTimestamp
    Date date;
}
