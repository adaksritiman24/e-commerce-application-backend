package com.sritiman.ecommerce.ecommerceapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiftCards {
    private String cardId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
