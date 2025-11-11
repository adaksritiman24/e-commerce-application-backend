package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.AssociatedProduct;
import com.sritiman.ecommerce.ecommerceapplication.entity.Product;
import com.sritiman.ecommerce.ecommerceapplication.model.product.SearchedProduct;
import com.sritiman.ecommerce.ecommerceapplication.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    Logger LOG = LoggerFactory.getLogger(ProductService.class);

    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Cacheable(cacheNames = "productsBySearchTerm", key = "#searchTerm")
    public List<SearchedProduct> getSearchByTerm(String searchTerm) {
        LOG.info("Finding products by search term: {}", searchTerm);
        ModelMapper modelMapper = new ModelMapper();
        List<Product> results = productRepository.findBySearchKeyword(searchTerm);
        return results
                .stream()
                .map(product -> modelMapper.map(product, SearchedProduct.class))
                .collect(Collectors.toList());
    }

    public List<SearchedProduct> getAssociatedProducts(List<AssociatedProduct> associatedProductsIds) {
        LOG.info("Finding products wrt associated product Ids: {}", associatedProductsIds);
        ModelMapper modelMapper = new ModelMapper();
        return associatedProductsIds.stream()
                .map(associatedProduct -> {
                    Optional<Product> intermediateProductData = productRepository.findById(associatedProduct.getId());
                    return intermediateProductData.map(product -> modelMapper.map(product, SearchedProduct.class)).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
