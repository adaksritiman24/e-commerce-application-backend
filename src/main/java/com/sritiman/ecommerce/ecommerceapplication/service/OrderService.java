package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.*;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.OrderNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.messaging.sender.CreateOrderJsonSender;
import com.sritiman.ecommerce.ecommerceapplication.model.OrderDTO;
import com.sritiman.ecommerce.ecommerceapplication.repository.CartRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.CustomerRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.OrderRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Autowired
    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, CustomerRepository customerRepository, ProductRepository productRepository, CreateOrderJsonSender createOrderJsonSender) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.createOrderJsonSender = createOrderJsonSender;
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
    @CacheEvict(cacheNames = "allOrdersForUser", key = "#customer.username")
    public Order placeOrder(Customer customer, PaymentDetails paymentDetails){
        LOG.info("Pacing order for user: {} with cartId: {}", customer.getUsername(), customer.getCart().getId());
        Cart customerCart = customer.getCart();
        Order order = cloneCartToOrder(customerCart, paymentDetails);
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

    private Order cloneCartToOrder(Cart cart, PaymentDetails paymentDetails) {
        Order order = new Order();
        order.setOrderEntryList(createOrderEntryList(cart.getCartEntryList()));
        order.setTotalPrice(cart.getTotalPrice());
        order.setDeliveryAddress(getCustomerDeliveryAddress(cart.getDeliveryAddress()));
        order.setPaymentDetails(paymentDetails);
        return order;
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

    @Cacheable(cacheNames = "allOrdersForUser", key = "#username")
    public List<Order> getAllOrders(String username) {
        LOG.info("Fetching all orders for username: {}", username);
        Customer customer = customerRepository.findByUsername(username);
        if(Objects.nonNull(customer)) {
            return orderRepository.findByCustomerUsername(customer.getId());
        }
        return Collections.emptyList();
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
