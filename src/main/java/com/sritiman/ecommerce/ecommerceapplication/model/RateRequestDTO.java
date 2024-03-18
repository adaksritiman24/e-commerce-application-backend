package com.sritiman.ecommerce.ecommerceapplication.model;

import lombok.Data;

@Data
public class RateRequestDTO {
    private String username;
    private int rating;
    private String text;
}
