package com.sritiman.ecommerce.ecommerceapplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "gift_card")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiftCard {
    @Id
    String id;
    BigDecimal amount;
    String title;
    @Column(length = 140)
    String description;
    @Column(name = "issuer")
    String issuerName;
    Boolean isActive;
    Boolean isRedeemed;
    @ManyToOne
    @JoinColumn(referencedColumnName = "username")
    Customer redeemedCustomer;
}
