package com.sritiman.ecommerce.ecommerceapplication.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    long id;

    String name;

    @ElementCollection
    List<ProductImage> images = new ArrayList<>();

    @Column(name = "normal_price")
    double normalPrice;

    @Column(name = "discounted_price")
    double discountedPrice;

    @ElementCollection
    List<Keyword> keywords = new ArrayList<>();

    //doubt
    @ManyToOne
    Category category;

    String brand;

    @Column(name = "product_details")
    @ElementCollection
    List<ProductDetail> productDetails = new ArrayList<>();

    @ElementCollection
    List<Review> reviews = new ArrayList<>();

    int rating;

    String seller;

    @ElementCollection
    List<AssociatedProduct> associatedProducts = new ArrayList<>();
}
