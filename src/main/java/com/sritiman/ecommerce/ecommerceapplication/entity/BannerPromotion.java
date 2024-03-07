package com.sritiman.ecommerce.ecommerceapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BannerPromotion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String image;

    @Column(name = "tag_line")
    String tagLine;

    String subheading;

    @Column(name = "button_text")
    String buttonText;

    String url;
}
