package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.*;
import com.sritiman.ecommerce.ecommerceapplication.repository.CartRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.CustomerRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
    OrderRepository orderRepository;
    CartRepository cartRepository;
    CustomerRepository customerRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
    }

    public Order placeOrder(Customer customer, PaymentDetails paymentDetails){
        Cart customerCart = customer.getCart();
        Order order = cloneCartToOrder(customerCart, paymentDetails);
        order.setCustomer(customer);

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
}