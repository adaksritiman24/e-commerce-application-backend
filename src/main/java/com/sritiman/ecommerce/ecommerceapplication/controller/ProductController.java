package com.sritiman.ecommerce.ecommerceapplication.controller;

import com.sritiman.ecommerce.ecommerceapplication.entity.AssociatedProduct;
import com.sritiman.ecommerce.ecommerceapplication.entity.Product;
import com.sritiman.ecommerce.ecommerceapplication.model.product.SearchedProduct;
import com.sritiman.ecommerce.ecommerceapplication.repository.ProductRepository;
import com.sritiman.ecommerce.ecommerceapplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/products")
@Transactional
@CrossOrigin
public class ProductController {


    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") long id){
       return productRepository.findById(id).orElse(null);
   }

   @GetMapping("/search/{text}")
    public List<SearchedProduct> getBySearchKeyword(@PathVariable("text") String searchTerm) {
        return productService.getSearchByTerm(searchTerm);
   }

   @PostMapping("/associated")
    public List<SearchedProduct> getAssociatedProductsData(@RequestBody List<AssociatedProduct> associatedProductsIds) {
        return productService.getAssociatedProducts(associatedProductsIds);
   }
}
