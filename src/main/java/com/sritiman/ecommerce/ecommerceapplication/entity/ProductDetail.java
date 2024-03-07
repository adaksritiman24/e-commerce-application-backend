package com.sritiman.ecommerce.ecommerceapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import jakarta.persistence.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDetail {

    String value;
}
