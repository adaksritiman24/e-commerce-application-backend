package com.sritiman.ecommerce.ecommerceapplication.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;
    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;
    @Column(name = "name")
    private String name;
    @Column(name="cvv", nullable = false)
    private String cvv;
    @Column(name="exp_date")
    private Date expDate;
    @Column(name="balance", nullable = false)
    private Double balance;

    @ElementCollection
    @Column(name="gift_cards")
    private List<GiftCards> giftCards = new ArrayList<>();
}
