package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.Customer;
import com.sritiman.ecommerce.ecommerceapplication.entity.Product;
import com.sritiman.ecommerce.ecommerceapplication.entity.Review;
import com.sritiman.ecommerce.ecommerceapplication.model.RateRequestDTO;
import com.sritiman.ecommerce.ecommerceapplication.repository.CustomerRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RatingService {

    Logger LOG = LoggerFactory.getLogger(RatingService.class);

    CustomerRepository customerRepository;
    ProductRepository productRepository;

    @Autowired
    public RatingService(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @CacheEvict(cacheNames = "productReviews", key = "#productId")
    public boolean giveRating(Long productId, RateRequestDTO rateRequestDTO) {
        LOG.info("Updating review for ProductId: {}", productId);
        String customerUsername = rateRequestDTO.getUsername();
        Customer customer = customerRepository.findByUsername(customerUsername);
        if (Objects.nonNull(customer) && Objects.nonNull(customer.getEmail())) { //check for registered customers
            Review review = new Review();
            review.setRating(rateRequestDTO.getRating());
            review.setCustomer(customer);
            review.setText(rateRequestDTO.getText());

            Optional<Product> productOpt = productRepository.findById(productId);
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                //remove existing user review before adding new review
                List<Review> existingReviews = product.getReviews().stream().filter(review1 -> !review1.getCustomer().getUsername().equalsIgnoreCase(customerUsername)).toList();
                ArrayList<Review> newReviews = new ArrayList<>(existingReviews);
                newReviews.add(review);

                product.setReviews(newReviews);
                //update product rating
                Integer productRating = newReviews.size() > 0 ? newReviews.stream().map(Review::getRating).reduce(0, Integer::sum) / newReviews.size() : 0;
                product.setRating(productRating);
                productRepository.save(product);
                return true;
            }
            return false;
        }
        return false;
    }

    @Cacheable(cacheNames = "productReviews", key = "#productId")
    public List<Review> getReviews(Long productId) {
        LOG.info("Fetching reviews for ProductId: {}", productId);
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            return productOptional.get().getReviews();
        }
        return Collections.emptyList();
    }
}
