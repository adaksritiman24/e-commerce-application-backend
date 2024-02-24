package com.sritiman.ecommerce.ecommerceapplication.utilities;

import com.sritiman.ecommerce.ecommerceapplication.entity.Accounts;
import com.sritiman.ecommerce.ecommerceapplication.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

import static com.sritiman.ecommerce.ecommerceapplication.constants.Constants.BUZZ_BUSINESS_ACCOUNT_NUMBER;
import static com.sritiman.ecommerce.ecommerceapplication.constants.Constants.CUSTOMER_DUMMY_ACCOUNT_NUMBER;

@Component
public class AccountsDataStub implements CommandLineRunner {

    @Autowired
    AccountsRepository accountsRepository;


    @Override
    public void run(String... args) throws Exception {

        try {
            System.out.println("Populating AccountData");
            Accounts businessAccount = new Accounts();
            businessAccount.setBalance(5.00);
            businessAccount.setAccountNumber(BUZZ_BUSINESS_ACCOUNT_NUMBER);
            businessAccount.setCardNumber("4000300020001000");
            businessAccount.setCvv("123");
            businessAccount.setExpDate(new Date());
            businessAccount.setGiftCards(Collections.emptyList());
            businessAccount.setName("Buzz.co.in");
            accountsRepository.save(businessAccount);

            Accounts customerAccount = new Accounts();
            customerAccount.setBalance(500000.00);
            customerAccount.setAccountNumber(CUSTOMER_DUMMY_ACCOUNT_NUMBER);
            customerAccount.setCardNumber("4111111111111111");
            customerAccount.setCvv("123");
            customerAccount.setExpDate(new Date());
            customerAccount.setGiftCards(Collections.emptyList());
            customerAccount.setName("Sritiman Adak");
            accountsRepository.save(customerAccount);
        }
        catch (Exception e) {
            System.out.println("[AccountStub error]: "+ e.getMessage());
        }
    }
}
