package com.sritiman.ecommerce.ecommerceapplication.model;

import com.sritiman.ecommerce.ecommerceapplication.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderEntryDTO {
    private long productId;
    private int quantity;
    private Double unitPrice;
    private Double totalPrice;
    private long id;
    private List<ProductImage> images;
    private String name;
    private String brand;
    private Double discountedPrice;
}