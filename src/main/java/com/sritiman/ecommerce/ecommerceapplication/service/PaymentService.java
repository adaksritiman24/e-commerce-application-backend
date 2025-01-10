package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.client.PaymentsClient;
import com.sritiman.ecommerce.ecommerceapplication.entity.Customer;
import com.sritiman.ecommerce.ecommerceapplication.entity.Order;
import com.sritiman.ecommerce.ecommerceapplication.entity.PaymentDetails;
import com.sritiman.ecommerce.ecommerceapplication.entity.PaymentMode;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomerNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.payments.UnsupportedPaymentModeException;
import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentAuthorizationRequest;
import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentResponseDTO;
import com.sritiman.ecommerce.ecommerceapplication.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentService {
    Logger LOG = LoggerFactory.getLogger(PaymentService.class);

    public static final String ORDER_SUCCESS = "SUCCESS";
    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final PaymentsClient paymentsClient;

    @Autowired
    public PaymentService(CustomerRepository customerRepository, OrderService orderService, PaymentsClient paymentsClient) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
        this.paymentsClient = paymentsClient;
    }

    public PaymentResponseDTO capturePayment(PaymentAuthorizationRequest paymentAuthorizationRequest) {
        String customerId = paymentAuthorizationRequest.getCustomerId();
        Customer customer = customerRepository.findByUsername(customerId);

        if(Objects.nonNull(customer)) {
            PaymentResponseDTO paymentResponseDTO = paymentsClient.authorizeCapture(paymentAuthorizationRequest);
            LOG.info("Got Payment response: {}", paymentResponseDTO);

            if(ORDER_SUCCESS.equals(paymentResponseDTO.getStatus())) {
                return refreshCustomerCartAndSendOrderAcknowledgement(customer, paymentAuthorizationRequest);
            }
            throw new UnsupportedPaymentModeException("Something went wrong in payments API");
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
