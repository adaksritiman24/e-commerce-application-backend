package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.BannerPromotion;
import com.sritiman.ecommerce.ecommerceapplication.entity.HomePageDisplayedCategory;
import com.sritiman.ecommerce.ecommerceapplication.repository.BannerPromotionsRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.HomePageDisplayedCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionsService {

    @Autowired
    BannerPromotionsRepository bannerPromotionsRepository;

    @Autowired
    HomePageDisplayedCategoriesRepository homePageDisplayedCategoriesRepository;

    public List<BannerPromotion> getAllPromotions() {
        return bannerPromotionsRepository.findAll();
    }

    public List<HomePageDisplayedCategory> getAllHomePageCategories() {
        return homePageDisplayedCategoriesRepository.findAll();
    }
}
