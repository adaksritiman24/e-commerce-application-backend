package com.sritiman.ecommerce.ecommerceapplication.controller;

import com.sritiman.ecommerce.ecommerceapplication.model.giftcard.GetGiftCardsResponse;
import com.sritiman.ecommerce.ecommerceapplication.model.giftcard.RedeemGiftCardRequest;
import com.sritiman.ecommerce.ecommerceapplication.service.GiftCardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gift-cards")
@CrossOrigin
@AllArgsConstructor
public class GiftCardController {

    private final GiftCardService giftCardService;

    @GetMapping("/{username}")
    public ResponseEntity<List<GetGiftCardsResponse>> getGiftCards(@PathVariable String username) {
        return ResponseEntity.ok(giftCardService.getGiftCards(username));
    }

    @PostMapping("/redeem")
    public ResponseEntity<String> redeemGiftCard(@Valid @RequestBody RedeemGiftCardRequest
                                                       redeemGiftCardRequest) {
        return ResponseEntity.ok(giftCardService.redeemGiftCard(redeemGiftCardRequest));
    }
}
