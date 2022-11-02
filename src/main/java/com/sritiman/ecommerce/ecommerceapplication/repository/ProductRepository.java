package com.sritiman.ecommerce.ecommerceapplication.repository;

import com.sritiman.ecommerce.ecommerceapplication.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    @Query(value = "select * from product where product.id in (select distinct p.id from product p inner join product_keywords k on p.id = k.product_id where k.value=?1)",
        nativeQuery = true)
    List<Product> findBySearchKeyword(String keyword);
}
