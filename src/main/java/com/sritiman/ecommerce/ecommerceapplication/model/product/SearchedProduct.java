package com.sritiman.ecommerce.ecommerceapplication.model.product;

import com.sritiman.ecommerce.ecommerceapplication.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchedProduct {
    long id;
    String name;
    List<ProductImage> images = new ArrayList<>();
    double normalPrice;
    double discountedPrice;
    Category category;
    String brand;
    int rating;
}
