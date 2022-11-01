package com.sritiman.ecommerce.ecommerceapplication.repository;

import com.sritiman.ecommerce.ecommerceapplication.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
}
