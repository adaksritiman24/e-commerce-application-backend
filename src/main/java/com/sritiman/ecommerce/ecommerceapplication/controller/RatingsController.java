package com.sritiman.ecommerce.ecommerceapplication.controller;

import com.sritiman.ecommerce.ecommerceapplication.entity.Review;
import com.sritiman.ecommerce.ecommerceapplication.model.RateRequestDTO;
import com.sritiman.ecommerce.ecommerceapplication.model.RateResponseDTO;
import com.sritiman.ecommerce.ecommerceapplication.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/ratings")
public class RatingsController {

    RatingService ratingService;

    @Autowired
    public RatingsController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<RateResponseDTO> giveRating(@PathVariable(name = "productId") Long productId, @RequestBody RateRequestDTO rateRequestDTO) {
        return new ResponseEntity<>(RateResponseDTO.builder().status(ratingService.giveRating(productId, rateRequestDTO)).build(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<Review>> getReviews(@PathVariable(name = "productId") Long productId) {
        return new ResponseEntity<>(ratingService.getReviews(productId), HttpStatus.OK);
    }
}
