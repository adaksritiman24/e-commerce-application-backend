package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.Customer;
import com.sritiman.ecommerce.ecommerceapplication.entity.GiftCard;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomBadException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomerNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.model.giftcard.GetGiftCardsResponse;
import com.sritiman.ecommerce.ecommerceapplication.model.giftcard.RedeemGiftCardRequest;
import com.sritiman.ecommerce.ecommerceapplication.repository.CustomerRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.GiftCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GiftCardService {
    private final GiftCardRepository giftCardRepository;
    private final CustomerRepository customerRepository;

    public List<GetGiftCardsResponse> getGiftCards(String username) {
        List<GiftCard> allGiftCardsForCustomer =
                giftCardRepository.getAllGiftCardsForCustomer(username,true);

        return allGiftCardsForCustomer.stream()
                .map(this::buildGetGiftCardsForCustomer)
                .collect(Collectors.toList());
    }

    private GetGiftCardsResponse buildGetGiftCardsForCustomer(GiftCard giftCard) {
        return GetGiftCardsResponse.builder()
                .id(giftCard.getId())
                .amount(giftCard.getAmount())
                .title(giftCard.getTitle())
                .description(giftCard.getDescription())
                .issuer(giftCard.getIssuerName())
                .build();
    }

    @Transactional
    public String redeemGiftCard(RedeemGiftCardRequest redeemGiftCardRequest) {
        Customer customer = customerRepository.findByUsername(redeemGiftCardRequest.getUsername());
        if(Objects.isNull(customer)) {
            throw new CustomerNotFoundException("No customer found with given username.");
        }
        GiftCard giftCard = giftCardRepository.findById(redeemGiftCardRequest.getGiftCardId())
                .orElseThrow(() -> new CustomNotFoundException("No gift card found for id: " +
                        redeemGiftCardRequest.getGiftCardId()));

        if(Boolean.TRUE.equals(giftCard.getIsRedeemed())) {
            throw new CustomBadException("This gift card is already redeemed.");
        }
        giftCard.setRedeemedCustomer(customer);
        giftCard.setIsRedeemed(true);
        return "Redeemed Successfully";
    }
}
