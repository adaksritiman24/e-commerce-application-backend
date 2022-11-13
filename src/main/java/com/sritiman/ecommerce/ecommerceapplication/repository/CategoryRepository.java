package com.sritiman.ecommerce.ecommerceapplication.repository;

import com.sritiman.ecommerce.ecommerceapplication.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String categoryName);
}
