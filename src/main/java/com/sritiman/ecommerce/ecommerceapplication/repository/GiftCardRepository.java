package com.sritiman.ecommerce.ecommerceapplication.repository;

import com.sritiman.ecommerce.ecommerceapplication.entity.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCardRepository extends JpaRepository<GiftCard, String> {

    @Query(value = "SELECT * FROM gift_card g WHERE g.redeemed_customer_username=:username "+
            " and g.is_redeemed=:isRedeemed", nativeQuery = true)
    List<GiftCard> getAllGiftCardsForCustomer(String username, Boolean isRedeemed);
}
