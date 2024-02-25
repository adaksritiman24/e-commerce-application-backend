package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.Accounts;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.payments.InsufficientBalanceException;
import com.sritiman.ecommerce.ecommerceapplication.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BuzzBankingService {
    private static final String ACCOUNT_BALANCE_IS_INSUFFICIENT = "Account Balance Is Insufficient";
    AccountsRepository accountsRepository;

    @Autowired
    public BuzzBankingService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Transactional
    public void doTransaction(Accounts fromAccount, Accounts toAccount, Double transferAmount) {
        if(fromAccount.getBalance() >= transferAmount) {
            Double customerBalance = fromAccount.getBalance() - transferAmount;
            Double merchantBalance = toAccount.getBalance() + transferAmount;
            fromAccount.setBalance(customerBalance);
            toAccount.setBalance(merchantBalance);
            accountsRepository.save(fromAccount);
            accountsRepository.save(toAccount);
        }
        else {
            throw new InsufficientBalanceException(ACCOUNT_BALANCE_IS_INSUFFICIENT);
        }
    }
}
