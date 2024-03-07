package com.sritiman.ecommerce.ecommerceapplication.entity;

import lombok.*;

import jakarta.persistence.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductImage {
    String url;
}
