package com.sritiman.ecommerce.ecommerceapplication.repository;

import com.sritiman.ecommerce.ecommerceapplication.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long>, JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM buzz_order o WHERE o.username=:username ORDER BY order_date DESC", nativeQuery = true)
    Page<Order> findByCustomerUsername(@Param("username") long username, PageRequest pageRequest);
}