package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.Cart;
import com.sritiman.ecommerce.ecommerceapplication.entity.Customer;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomerSignupDatabaseException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.LoginException;
import com.sritiman.ecommerce.ecommerceapplication.model.LoginResponse;
import com.sritiman.ecommerce.ecommerceapplication.model.SignupRequest;
import com.sritiman.ecommerce.ecommerceapplication.model.SignupResponse;
import com.sritiman.ecommerce.ecommerceapplication.model.UpdateCartRequest;
import com.sritiman.ecommerce.ecommerceapplication.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final ModelMapper modelMapper = new ModelMapper();
    private final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    private CustomerRepository customerRepository;
    private CartService cartService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CartService cartService) {
        this.customerRepository = customerRepository;
        this.cartService = cartService;
    }

    public SignupResponse signup(SignupRequest signupRequest) {
        Customer customerToBeCreated = modelMapper.map(signupRequest, Customer.class);
        customerToBeCreated.setPassword(hashPw(customerToBeCreated.getPassword()));

        checkForDuplicateUsername(customerToBeCreated.getUsername());
        checkForDuplicateEmail(customerToBeCreated.getEmail());

        try{
            Customer newCustomerData = customerRepository.save(customerToBeCreated);
            String token = generateNewAuthToken(newCustomerData.getUsername(),newCustomerData.getPassword());
            return new SignupResponse(newCustomerData, token);
        }
        catch (Exception e) {
            throw new CustomerSignupDatabaseException("Something Went Wrong!");
        }
    }


    public LoginResponse login(String username, String password, String anonymousCartUsername) throws LoginException {
        Customer customer = customerRepository.findByUsername(username);
        if(customer != null) {
            boolean matchFound = BCrypt.checkpw(password, customer.getPassword());
            if(matchFound) {
                //merge with existing cart
                Customer anonymousUser = customerRepository.findByUsername(anonymousCartUsername);
                if(Objects.nonNull(anonymousUser)) {
                    Cart anonymousUserCart = anonymousUser.getCart();
                    try {
                        cartService.mergeCart(username, anonymousUserCart.getCartEntryList()
                                .stream()
                                .map(cartEntry -> new UpdateCartRequest(cartEntry.getProductId(), cartEntry.getQuantity()))
                                .collect(Collectors.toList()));
                        //Remove anonymous user once cartMerge is done
                        customerRepository.delete(anonymousUser);
                    }
                    catch (Exception e) {
                        LOG.error(e.getMessage());
                    }
                }
                return new LoginResponse(customer,generateNewAuthToken(customer.getUsername(), customer.getPassword()));
            }
            else
                throw new LoginException("Invalid-password invalid");
        }
        else
            throw new LoginException("Invalid-username invalid");
    }

    private String generateNewAuthToken(String username, String password) {
        return Base64.getEncoder().encodeToString((username+":"+ password).getBytes());
    }

    private void checkForDuplicateUsername(String username) throws CustomerSignupDatabaseException{
        if(customerRepository.findByUsername(username) != null) throw new CustomerSignupDatabaseException("username already exists");
    }

    private void checkForDuplicateEmail(String email) throws CustomerSignupDatabaseException{
        if(customerRepository.findByEmail(email) != null) throw new CustomerSignupDatabaseException("email already exists");
    }

    private String hashPw(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public Customer getMe(String token) throws LoginException{
        try{
            String decodedToken = new String(Base64.getDecoder().decode(token));
            String username = Arrays.stream(decodedToken.split(":")).findFirst().orElse(null);

            return customerRepository.findByUsername(username);
        }
        catch (Exception exception){
            throw new LoginException("Invalid Token");
        }
    }
}
