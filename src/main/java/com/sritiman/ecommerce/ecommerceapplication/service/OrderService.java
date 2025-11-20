package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.*;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.OrderNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.messaging.sender.CreateOrderJsonSender;
import com.sritiman.ecommerce.ecommerceapplication.model.OrderDTO;
import com.sritiman.ecommerce.ecommerceapplication.model.payments.PaymentAuthorizationRequest;
import com.sritiman.ecommerce.ecommerceapplication.model.response.OrderHistoryItemDTO;
import com.sritiman.ecommerce.ecommerceapplication.model.response.OrderHistoryResponseDTO;
import com.sritiman.ecommerce.ecommerceapplication.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class OrderService {
    Logger LOG = LoggerFactory.getLogger(OrderService.class);

    OrderRepository orderRepository;
    CartRepository cartRepository;
    CustomerRepository customerRepository;
    ProductRepository productRepository;
    CreateOrderJsonSender createOrderJsonSender;
    GiftCardRepository giftCardRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartRepository cartRepository,
                        CustomerRepository customerRepository, ProductRepository productRepository,
                        CreateOrderJsonSender createOrderJsonSender, GiftCardRepository giftCardRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.createOrderJsonSender = createOrderJsonSender;
        this.giftCardRepository = giftCardRepository;
    }

    public OrderDTO getOrder(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        ModelMapper modelMapper = new ModelMapper();
        if(orderOpt.isPresent()) {
            OrderDTO orderDto = modelMapper.map(orderOpt.get(), OrderDTO.class);
            orderDto.getOrderEntryList()
                    .forEach(orderEntry -> {
                        Product productInfo = productRepository.findById(orderEntry.getProductId()).orElse(null);
                        if(Objects.nonNull(productInfo)) {
                            orderEntry.setId(productInfo.getId());
                            orderEntry.setName(productInfo.getName());
                            orderEntry.setBrand(productInfo.getBrand());
                            orderEntry.setDiscountedPrice(productInfo.getDiscountedPrice());
                            orderEntry.setImages(productInfo.getImages());
                        }
                    });
            return orderDto;
        }
        throw new OrderNotFoundException("Order Not Found!");
    }

    @Transactional
    public Order placeOrder(Customer customer, PaymentAuthorizationRequest paymentAuthorizationRequest){
        LOG.info("Pacing order for user: {} with cartId: {}", customer.getUsername(), customer.getCart().getId());
        Cart customerCart = customer.getCart();
        Order order = cloneCartToOrder(customerCart, paymentAuthorizationRequest);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.CREATED);

        if(Objects.isNull(customer.getEmail())) { //guest customer
            customer.setCart(null);
        }
        else{
            customer.setCart(new Cart());
        }
        customerRepository.save(customer);
        cartRepository.delete(customerCart);

        return orderRepository.save(order);
    }

    private Order cloneCartToOrder(Cart cart, PaymentAuthorizationRequest paymentAuthorizationRequest) {
        Order order = new Order();
        order.setOrderEntryList(createOrderEntryList(cart.getCartEntryList()));
        order.setTotalPrice(cart.getTotalPrice());
        order.setDeliveryAddress(getCustomerDeliveryAddress(cart.getDeliveryAddress()));
        order.setPaymentDetails(getPaymentDetails(paymentAuthorizationRequest));
        setGiftCardDetails(paymentAuthorizationRequest, order);
        inActivateGiftCard(paymentAuthorizationRequest.getGiftCard().getGiftCardId());
        return order;
    }

    private void setGiftCardDetails(PaymentAuthorizationRequest paymentAuthorizationRequest, Order order) {
        order.setIsGiftCardApplied(paymentAuthorizationRequest.getIsGiftCardApplied());
        order.setGiftCardId(paymentAuthorizationRequest.getGiftCard().getGiftCardId());
    }

    private void inActivateGiftCard(String giftCardId) {
        GiftCard giftCard = giftCardRepository.findById(giftCardId)
                .orElseThrow(() -> new CustomNotFoundException("No gift cards found with this id"));
        giftCard.setIsActive(Boolean.FALSE);
        giftCardRepository.save(giftCard);
    }

    private PaymentDetails getPaymentDetails(PaymentAuthorizationRequest
                                             paymentAuthorizationRequest) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPaymentMode(PaymentMode.BANK_CARD);
        paymentDetails.setTotalAmount(paymentAuthorizationRequest.getCost().getTotalCost());
        return paymentDetails;
    }

    private List<CartEntry> createOrderEntryList(List<CartEntry> cartEntryList) {

        List<CartEntry> orderEntries = new ArrayList<>();
        cartEntryList.forEach(cartEntry -> {
            CartEntry orderEntry = new CartEntry();
            orderEntry.setProductId(cartEntry.getProductId());
            orderEntry.setQuantity(cartEntry.getQuantity());
            orderEntry.setUnitPrice(cartEntry.getUnitPrice());
            orderEntry.setTotalPrice(cartEntry.getTotalPrice());
            orderEntries.add(orderEntry);
        });

        return orderEntries;
    }
    private Address getCustomerDeliveryAddress(Address cartAddress) {
        Address address = new Address();
        address.setName(cartAddress.getName());
        address.setPhone(cartAddress.getPhone());
        address.setEmail(cartAddress.getEmail());
        address.setPincode(cartAddress.getPincode());
        address.setLocality(cartAddress.getLocality());
        address.setHouse(cartAddress.getHouse());
        address.setCity(cartAddress.getCity());
        address.setCountry(cartAddress.getCountry());
        return address;
    }

    public OrderHistoryResponseDTO getAllOrders(String username, int pageNumber) {
        LOG.info("Fetching all orders for username: {}", username);
        Customer customer = customerRepository.findByUsername(username);
        if(Objects.nonNull(customer)) {
            PageRequest pageRequest = PageRequest.of(pageNumber, 3);
            Page<Order> orderPageData = orderRepository.findByCustomerUsername(customer.getId(), pageRequest);
            int totalPages = orderPageData.getTotalPages();
            return getOrderHistoryResponse(orderPageData.getContent(), totalPages, pageNumber);
        }
        return OrderHistoryResponseDTO.builder().totalPages(0).requestedPageNumber(0).orders(Collections.emptyList()).build();
    }

    private OrderHistoryResponseDTO getOrderHistoryResponse(List<Order> orders, int totalPages, int requestedPage) {
        List<OrderHistoryItemDTO> orderHistoryItemDTOS = orders.stream()
                .map(order -> OrderHistoryItemDTO.builder()
                        .orderDate(order.getOrderDate())
                        .id(order.getId())
                        .status(order.getStatus())
                        .totalPrice(order.getTotalPrice())
                        .build())
                .toList();
        return OrderHistoryResponseDTO.builder()
                .totalPages(totalPages)
                .requestedPageNumber(requestedPage)
                .orders(orderHistoryItemDTOS)
                .build();
    }

    public void sendOrderJSON(Order order) {
        try {
            createOrderJsonSender.sendMessage(order);
        }
        catch (Exception ex) {
            log.error("Failed to send order JSON with error: ", ex);
        }
    }
}
