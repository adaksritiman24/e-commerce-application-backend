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
public class HomePageDisplayedCategory {

    @Id
    long id;
    String name;
    @OneToOne(targetEntity = Category.class, cascade = CascadeType.ALL)
    Category category;
    String image;
}
