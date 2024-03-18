package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.Customer;
import com.sritiman.ecommerce.ecommerceapplication.entity.Product;
import com.sritiman.ecommerce.ecommerceapplication.entity.Review;
import com.sritiman.ecommerce.ecommerceapplication.model.RateRequestDTO;
import com.sritiman.ecommerce.ecommerceapplication.repository.CustomerRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RatingService {

    CustomerRepository customerRepository;
    ProductRepository productRepository;

    @Autowired
    public RatingService(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public boolean giveRating(Long productId, RateRequestDTO rateRequestDTO) {
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
                productRepository.save(product);
                return true;
            }
            return false;
        }
        return false;
    }

    public List<Review> getReviews(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            return productOptional.get().getReviews();
        }
        return Collections.emptyList();
    }
}
