package com.sritiman.ecommerce.ecommerceapplication.utilities;

import com.sritiman.ecommerce.ecommerceapplication.entity.Address;
import com.sritiman.ecommerce.ecommerceapplication.entity.Cart;
import com.sritiman.ecommerce.ecommerceapplication.entity.Customer;
import com.sritiman.ecommerce.ecommerceapplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataStub implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDataStub(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        String password = BCrypt.hashpw("Password@1", BCrypt.gensalt());

        var address = new Address();
        address.setHouse("22");
        address.setLocality("Surya Sen Pally");
        address.setCountry("India");
        address.setPincode("700082");
        address.setCity("Kolkata");

        var customer = new Customer();

        customer.setName("Sritiman Adak");
        customer.setEmail("adaksritiman24@gmail.com");
        customer.setAddress(address);
        customer.setUsername("sritiman24");
        customer.setPhoneNumber("6289403930");
        customer.setPassword(password);
        customer.setCart(new Cart());

        System.out.println(customer);
        try {
            customerRepository.delete(customerRepository.findByUsername("sritiman24"));
            customerRepository.save(customer);

        } catch (Exception e) {
            System.out.println("[FAIL]: " + e.getMessage());
        }
    }

}
