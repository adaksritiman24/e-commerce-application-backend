package com.sritiman.ecommerce.ecommerceapplication.utilities;

import com.sritiman.ecommerce.ecommerceapplication.entity.Category;
import com.sritiman.ecommerce.ecommerceapplication.entity.HomePageDisplayedCategory;
import com.sritiman.ecommerce.ecommerceapplication.repository.CategoryRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.HomePageDisplayedCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class HomePageCategoriesStub implements CommandLineRunner {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    HomePageDisplayedCategoriesRepository homePageDisplayedCategoriesRepository;
    @Override
    public void run(String... args) throws Exception {
//        var c1= new HomePageDisplayedCategory(1, "Fashion", categoryRepository.findByName("fashion"), "/buzz/imgs/categories/fashion.jpeg");
//        var c2= new HomePageDisplayedCategory(2, "Smartphones", categoryRepository.findByName("smartphones"), "/buzz/imgs/categories/smartphones.jpeg");
//        var c3= new HomePageDisplayedCategory(3, "Watches", categoryRepository.findByName("watches"),"/buzz/imgs/categories/watches.webp");
//        var c4= new HomePageDisplayedCategory(4, "Home and Kitchen", categoryRepository.findByName("home and kitchen"),"/buzz/imgs/categories/kitchen.jpeg");
//        var c5= new HomePageDisplayedCategory(5, "Footwear", categoryRepository.findByName("footwear"),"/buzz/imgs/categories/footwear.webp");
//        var c6= new HomePageDisplayedCategory(5, "Baby", categoryRepository.findByName("baby products"),"/buzz/imgs/categories/baby.jpeg");
//
//        homePageDisplayedCategoriesRepository.save(c1);
//        homePageDisplayedCategoriesRepository.save(c2);
//        homePageDisplayedCategoriesRepository.save(c3);
//        homePageDisplayedCategoriesRepository.save(c4);
//        homePageDisplayedCategoriesRepository.save(c5);
//        homePageDisplayedCategoriesRepository.save(c6);

    }
}
