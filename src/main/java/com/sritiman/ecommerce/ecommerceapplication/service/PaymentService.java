package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.Customer;
import com.sritiman.ecommerce.ecommerceapplication.entity.Order;
import com.sritiman.ecommerce.ecommerceapplication.entity.PaymentDetails;
import com.sritiman.ecommerce.ecommerceapplication.entity.PaymentMode;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomerNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.payments.UnsupportedPaymentModeException;
import com.sritiman.ecommerce.ecommerceapplication.gateway.BankPaymentGateway;
import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentAuthorizationRequest;
import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentModeDTO;
import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentResponseDTO;
import com.sritiman.ecommerce.ecommerceapplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class PaymentService {
    public static final String ORDER_SUCCESS = "SUCCESS";
    private final CustomerRepository customerRepository;
    private final BankPaymentGateway bankPaymentGateway;
    private final OrderService orderService;

    @Autowired
    public PaymentService(CustomerRepository customerRepository, BankPaymentGateway bankPaymentGateway, OrderService orderService) {
        this.customerRepository = customerRepository;
        this.bankPaymentGateway = bankPaymentGateway;
        this.orderService = orderService;
    }

    public PaymentResponseDTO capturePayment(PaymentAuthorizationRequest paymentAuthorizationRequest) {
        String customerId = paymentAuthorizationRequest.getCustomerId();
        Customer customer = customerRepository.findByUsername(customerId);

        if(Objects.nonNull(customer)) {
            if(PaymentModeDTO.BANK_CARD.equals(paymentAuthorizationRequest.getPaymentMode())) {
                String captureStatus = bankPaymentGateway.capture(paymentAuthorizationRequest);

                if(captureStatus.equalsIgnoreCase(ORDER_SUCCESS)) {
                    return refreshCustomerCartAndSendOrderAcknowledgement(customer, paymentAuthorizationRequest);
                }
            }else {
                throw new UnsupportedPaymentModeException("PaymentMode not Supported");
            }
        }
        throw new CustomerNotFoundException("Customer not Found");
    }

    private PaymentResponseDTO refreshCustomerCartAndSendOrderAcknowledgement(Customer customer, PaymentAuthorizationRequest paymentAuthorizationRequest) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPaymentMode(PaymentMode.BANK_CARD);
        paymentDetails.setTotalAmount(paymentAuthorizationRequest.getCost().getTotalCost());
        Order order = orderService.placeOrder(customer, paymentDetails);
        return preparePaymentResponseDTO(order.getId());
    }

    private PaymentResponseDTO preparePaymentResponseDTO(Long orderId) {
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        paymentResponseDTO.setStatus("SUCCESS");
        paymentResponseDTO.setOrderId(orderId);
        return  paymentResponseDTO;
    }
}
