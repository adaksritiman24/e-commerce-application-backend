package com.sritiman.ecommerce.ecommerceapplication.controller;


import com.sritiman.ecommerce.ecommerceapplication.entity.BannerPromotion;
import com.sritiman.ecommerce.ecommerceapplication.entity.HomePageDisplayedCategory;
import com.sritiman.ecommerce.ecommerceapplication.service.PromotionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionsController {

    @Autowired
    PromotionsService promotionsService;

    @GetMapping("/banner")
    public ResponseEntity<List<BannerPromotion>> getAllBannerPromotions() {
        return new ResponseEntity<>(
                promotionsService.getAllPromotions(), HttpStatus.OK
        );
    }

    @GetMapping("/categories")
    public ResponseEntity<List<HomePageDisplayedCategory>> getHomePageCategories() {
        return new ResponseEntity<>(
                promotionsService.getAllHomePageCategories(), HttpStatus.OK
        );
    }
}
