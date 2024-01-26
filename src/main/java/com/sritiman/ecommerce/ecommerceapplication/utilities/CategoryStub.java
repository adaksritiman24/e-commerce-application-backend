package com.sritiman.ecommerce.ecommerceapplication.utilities;

import com.sritiman.ecommerce.ecommerceapplication.entity.Category;
import com.sritiman.ecommerce.ecommerceapplication.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CategoryStub implements CommandLineRunner {

    private CategoryRepository categoryRepository;

    public CategoryStub(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args){
        try {
            List<Category> categories = Arrays.asList(
                new Category(1, "electronics"),
                new Category(2, "home and kitchen"),
                new Category(3, "fashion"),
                new Category(4, "smartphones"),
                new Category(5, "computers"),
                new Category(6, "lifestyle"),
                new Category(7, "beauty"),
                new Category(8, "footwear"),
                new Category(9, "baby products")
            );

            categoryRepository.saveAll(categories);
        }catch (Exception e) {
            System.out.println("[FAIL]: " + e.getMessage());
        }
    }
}
