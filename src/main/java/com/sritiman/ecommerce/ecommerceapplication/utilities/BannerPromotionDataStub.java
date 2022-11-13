package com.sritiman.ecommerce.ecommerceapplication.utilities;

import com.sritiman.ecommerce.ecommerceapplication.entity.BannerPromotion;
import com.sritiman.ecommerce.ecommerceapplication.repository.BannerPromotionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BannerPromotionDataStub implements CommandLineRunner {

    @Autowired
    BannerPromotionsRepository bannerPromotionsRepository;

    @Override
    public void run(String... args) throws Exception {
//        System.out.println("here");
//        var bp1 = new BannerPromotion(0, "/buzz/imgs/banner/diwali.webp", "Special Diwali Offer!", "Get up to 40% off on smartphones", "Shop Now", "/category?id=50");
//        var bp2 = new BannerPromotion(1, "/buzz/imgs/banner/fashion.jpeg","Buzz Super-Bonanza offer", "Amazing Mens' tshirts starting from only ₹599", "Grab your deal", "/category?id=49");
//        var bp3 = new BannerPromotion(2, "/buzz/imgs/banner/beauty-products.jpeg","Great Deals on Beauty Products","Explore the wide range of beauty products and get exciting offers","Explore","/category?id=53");
//
//        bannerPromotionsRepository.save(bp1);
//        bannerPromotionsRepository.save(bp2);
//        bannerPromotionsRepository.save(bp3);
//        System.out.println("deone...");
    }
}
