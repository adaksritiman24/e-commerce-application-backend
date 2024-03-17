package com.sritiman.ecommerce.ecommerceapplication.repository;

import com.sritiman.ecommerce.ecommerceapplication.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM buzz_order o WHERE o.username=:username ORDER BY order_date DESC", nativeQuery = true)
    List<Order> findByCustomerUsername(@Param("username") long username);
}