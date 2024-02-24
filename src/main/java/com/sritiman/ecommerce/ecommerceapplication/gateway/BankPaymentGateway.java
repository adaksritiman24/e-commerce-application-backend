package com.sritiman.ecommerce.ecommerceapplication.gateway;

import com.sritiman.ecommerce.ecommerceapplication.entity.Accounts;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.payments.AccountNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentAuthorizationRequest;
import com.sritiman.ecommerce.ecommerceapplication.repository.AccountsRepository;
import com.sritiman.ecommerce.ecommerceapplication.service.BuzzBankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.sritiman.ecommerce.ecommerceapplication.constants.Constants.BUZZ_BUSINESS_ACCOUNT_NUMBER;

@Service
public class BankPaymentGateway implements PaymentGateway{
    private static final String ACCOUNT_NOT_FOUND = "Account not found";
    private static final String SUCCESS = "success";
    BuzzBankingService buzzBankingService;
    AccountsRepository accountsRepository;

    @Autowired
    public BankPaymentGateway(BuzzBankingService buzzBankingService, AccountsRepository accountsRepository) {
        this.buzzBankingService = buzzBankingService;
        this.accountsRepository = accountsRepository;
    }

    @Override
    public String capture(PaymentAuthorizationRequest paymentAuthorizationRequest) {

        String customerCardNumber = paymentAuthorizationRequest.getBankCard().getCardNumber();
        String cvv = paymentAuthorizationRequest.getBankCard().getCvv();

        Accounts merchantAccount = accountsRepository.findByAccountNumber(BUZZ_BUSINESS_ACCOUNT_NUMBER);
        Accounts customerAccount = accountsRepository.findByCardNumberAndCvv(customerCardNumber, cvv);

        if(Objects.isNull(merchantAccount)) {
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND, "merchant Account");
        }
        if(Objects.isNull(customerAccount)) {
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND, "customer Account");
        }
        double totalTransferAmount = paymentAuthorizationRequest.getCost().getTotalCost();
        buzzBankingService.doTransaction(customerAccount, merchantAccount, totalTransferAmount);

        return SUCCESS;
    }
}
