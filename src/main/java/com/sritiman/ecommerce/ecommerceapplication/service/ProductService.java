package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.Product;
import com.sritiman.ecommerce.ecommerceapplication.model.product.SearchedProduct;
import com.sritiman.ecommerce.ecommerceapplication.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<SearchedProduct> getSearchByTerm(String searchTerm) {
        ModelMapper modelMapper = new ModelMapper();
        List<Product> results = productRepository.findBySearchKeyword(searchTerm);
        return results
                .stream()
                .map(product -> modelMapper.map(product, SearchedProduct.class))
                .collect(Collectors.toList());
    }
}
