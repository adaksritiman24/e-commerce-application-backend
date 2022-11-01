package com.sritiman.ecommerce.ecommerceapplication.controller;

import com.sritiman.ecommerce.ecommerceapplication.entity.Product;
import com.sritiman.ecommerce.ecommerceapplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/products")
@Transactional
public class ProductController {

    @Autowired
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

   @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") long id){
       return productRepository.findById(id).orElse(null);
   }
}
