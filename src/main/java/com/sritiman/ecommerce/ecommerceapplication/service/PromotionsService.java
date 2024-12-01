package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.BannerPromotion;
import com.sritiman.ecommerce.ecommerceapplication.entity.HomePageDisplayedCategory;
import com.sritiman.ecommerce.ecommerceapplication.repository.BannerPromotionsRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.HomePageDisplayedCategoriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionsService {

    Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BannerPromotionsRepository bannerPromotionsRepository;

    @Autowired
    HomePageDisplayedCategoriesRepository homePageDisplayedCategoriesRepository;

    @Cacheable(cacheNames = "allPromotions")
    public List<BannerPromotion> getAllPromotions() {
        LOG.info("Fetching all promotions from DB");
        return bannerPromotionsRepository.findAll();
    }

    @Cacheable(cacheNames = "homePageCategories")
    public List<HomePageDisplayedCategory> getAllHomePageCategories() {
        LOG.info("Fetching homepageCategories from DB");
        return homePageDisplayedCategoriesRepository.findAll();
    }
}
