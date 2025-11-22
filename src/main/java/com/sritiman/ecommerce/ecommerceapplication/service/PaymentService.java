package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.client.PaymentsClient;
import com.sritiman.ecommerce.ecommerceapplication.entity.*;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomBadException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomerNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.payments.UnsupportedPaymentModeException;
import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentAuthorizationRequest;
import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentResponseDTO;
import com.sritiman.ecommerce.ecommerceapplication.repository.CustomerRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.GiftCardRepository;
import com.sritiman.ecommerce.ecommerceapplication.rules.GiftCardPaymentAuthorizationRule;
import com.sritiman.ecommerce.ecommerceapplication.rules.PaymentAuthorizationRuleEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentService {
    Logger LOG = LoggerFactory.getLogger(PaymentService.class);

    public static final String ORDER_SUCCESS = "SUCCESS";
    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final PaymentsClient paymentsClient;
    private final GiftCardRepository giftCardRepository;

    @Autowired
    public PaymentService(CustomerRepository customerRepository, OrderService orderService,
                          PaymentsClient paymentsClient, GiftCardRepository giftCardRepository) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
        this.paymentsClient = paymentsClient;
        this.giftCardRepository = giftCardRepository;
    }

    public PaymentResponseDTO capturePayment(PaymentAuthorizationRequest paymentAuthorizationRequest) {
        String customerId = paymentAuthorizationRequest.getCustomerId();
        Customer customer = customerRepository.findByUsername(customerId);

        validateGiftCardDetails(paymentAuthorizationRequest);
        validateCustomerNotNull(customer);

        PaymentAuthorizationRuleEngine paymentAuthorizationRuleEngine =
                new PaymentAuthorizationRuleEngine(
                        List.of(new GiftCardPaymentAuthorizationRule())
                );
        paymentAuthorizationRuleEngine.applyAll(paymentAuthorizationRequest);

        if (paymentAuthorizationRequest.getCost().isPaymentRequired()) {
            makePaymentThroughPaymentGateway(paymentAuthorizationRequest);
        }
        return refreshCustomerCartAndSendOrderAcknowledgement(customer, paymentAuthorizationRequest);
    }

    private void makePaymentThroughPaymentGateway(PaymentAuthorizationRequest paymentAuthorizationRequest) {
        PaymentResponseDTO paymentResponseDTO = paymentsClient.authorizeCapture(paymentAuthorizationRequest);
        LOG.info("Got Payment response: {}", paymentResponseDTO);

        if (!ORDER_SUCCESS.equals(paymentResponseDTO.getStatus())) {
            throw new UnsupportedPaymentModeException("Something went wrong in payments API");
        }
    }

    private void validateCustomerNotNull(Customer customer) {
        if(Objects.isNull(customer)) {
            throw new CustomerNotFoundException("Customer not Found");
        }
    }

    public void validateGiftCardDetails(PaymentAuthorizationRequest paymentAuthorizationRequest) {
        if(!Boolean.TRUE.equals(paymentAuthorizationRequest.getIsGiftCardApplied())) {
            return;
        }
        if(Objects.isNull(paymentAuthorizationRequest.getGiftCard())) {
            throw new CustomBadException("giftCard object cannot be null");
        }
        Optional<GiftCard> giftCardOPT = giftCardRepository
                .findById(paymentAuthorizationRequest.getGiftCard().getGiftCardId());
        if(giftCardOPT.isPresent()) {
            GiftCard giftCard = giftCardOPT.get();
            if(!giftCard.getIsActive()) {
                throw new CustomBadException("This Gift Card is already used");
            }
            if(!giftCard.getIsRedeemed()) {
                throw new CustomBadException("This gift cards is not redeemed by any customer");
            }
        }
        else {
            throw new CustomNotFoundException("No gift cards found with this id");
        }
    }

    private PaymentResponseDTO refreshCustomerCartAndSendOrderAcknowledgement(Customer customer,
                                                                              PaymentAuthorizationRequest paymentAuthorizationRequest) {
        Order order = orderService.placeOrder(customer, paymentAuthorizationRequest);
        orderService.sendOrderJSON(order);
        return preparePaymentResponseDTO(order.getId());
    }

    private PaymentResponseDTO preparePaymentResponseDTO(Long orderId) {
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        paymentResponseDTO.setStatus("SUCCESS");
        paymentResponseDTO.setOrderId(orderId);
        return  paymentResponseDTO;
    }
}
