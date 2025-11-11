package com.sritiman.ecommerce.ecommerceapplication.service;

import com.sritiman.ecommerce.ecommerceapplication.entity.Cart;
import com.sritiman.ecommerce.ecommerceapplication.entity.Customer;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomerSignupDatabaseException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.EmailAlreadyExistsException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.LoginException;
import com.sritiman.ecommerce.ecommerceapplication.model.*;
import com.sritiman.ecommerce.ecommerceapplication.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sritiman.ecommerce.ecommerceapplication.constants.Constants.OAUTH_DEFAULT_PASSWORD;

@Service
public class CustomerService {
    
    private final ModelMapper modelMapper = new ModelMapper();
    private final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final CartService cartService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CartService cartService) {
        this.customerRepository = customerRepository;
        this.cartService = cartService;
    }

    public SignupResponse signup(SignupRequest signupRequest) {
        checkForDuplicateUsername(signupRequest.getUsername());
        checkForDuplicateEmail(signupRequest.getEmail());
        try{
            Customer newRegisteredCustomer = createNewSignupCustomer(signupRequest);
            Customer anonymousUser = customerRepository.findByUsername(signupRequest.getAnonymousCartUsername());
            mergeAnonymousCartWithRegisteredCustomerCartAndDeleteAnonymousUser(anonymousUser, newRegisteredCustomer);
            
            String token = generateNewAuthToken(newRegisteredCustomer.getUsername(),newRegisteredCustomer.getPassword());
            return new SignupResponse(newRegisteredCustomer, token);
        }
        catch (Exception e) {
            throw new CustomerSignupDatabaseException("Something Went Wrong!");
        }
    }

    private Customer createNewSignupCustomer(SignupRequest signupRequest) {
        Customer customerToBeCreated = modelMapper.map(signupRequest, Customer.class);
        customerToBeCreated.setPassword(hashPw(customerToBeCreated.getPassword()));
        customerToBeCreated.setCart(new Cart());
        customerToBeCreated.getAddress().setPhone(customerToBeCreated.getPhoneNumber());
        customerToBeCreated.getAddress().setEmail(customerToBeCreated.getEmail());
        customerToBeCreated.getAddress().setName(customerToBeCreated.getName());

        return customerRepository.save(customerToBeCreated);
    }

    public LoginResponse loginWithOauthUser(String username, OauthUserDetails oauthUserDetails,
                                            String anonymousCartUsername) {
        Customer registeredCustomer = customerRepository.findByUsername(username);
        if(Objects.nonNull(registeredCustomer)) {
            return initiateLoginForExistingOAuthCustomer(username, oauthUserDetails,
                    anonymousCartUsername, registeredCustomer);
        }
        validateEmailAlreadyTaken(username);
        return initiateLoginForNewOAuthCustomer(username,
                oauthUserDetails, anonymousCartUsername);
    }

    private LoginResponse initiateLoginForNewOAuthCustomer(String username, OauthUserDetails oauthUserDetails,
                                                           String anonymousCartUsername) {
        Customer newCustomer = buildNewOauthCustomer(username, oauthUserDetails);
        Customer anonymousCustomer = customerRepository.findByUsername(anonymousCartUsername);

        mergeAnonymousCartWithRegisteredCustomerCartAndDeleteAnonymousUser(anonymousCustomer, newCustomer);
        String token = generateNewAuthToken(newCustomer.getUsername(),newCustomer.getPassword());
        return new LoginResponse(newCustomer, token);
    }

    private Customer buildNewOauthCustomer(String username, OauthUserDetails userDetails) {
        Customer newOauthCustomer = new Customer();
        newOauthCustomer.setUsername(username);
        newOauthCustomer.setEmail(username);
        newOauthCustomer.setName(userDetails.getFirstName()+" "+ userDetails.getLastName());
        newOauthCustomer.setPassword(hashPw(OAUTH_DEFAULT_PASSWORD));
        newOauthCustomer.setCart(new Cart());
        newOauthCustomer.setProfilePicture(userDetails.getProfilePicture());

        return customerRepository.save(newOauthCustomer);
    }

    private void validateEmailAlreadyTaken(String username) {
        Customer customerByEmail = customerRepository.findByEmail(username);
        if(Objects.nonNull(customerByEmail)) {
            throw new EmailAlreadyExistsException("Email Id already exists!");
        }
    }

    private LoginResponse initiateLoginForExistingOAuthCustomer(String username, OauthUserDetails oauthUserDetails,
                                                                String anonymousCartUsername, Customer registeredCustomer) {
        boolean isValidOAuthCustomer = BCrypt.checkpw(OAUTH_DEFAULT_PASSWORD, registeredCustomer.getPassword());
        if(!isValidOAuthCustomer) {
            throw new LoginException("An user with username "+ username +" already exists!");
        }
        registeredCustomer.setProfilePicture(oauthUserDetails.getProfilePicture());
        Customer anonymousCustomer = customerRepository.findByUsername(anonymousCartUsername);
        mergeAnonymousCartWithRegisteredCustomerCartAndDeleteAnonymousUser(anonymousCustomer, registeredCustomer);
        return new LoginResponse(registeredCustomer, 
                generateNewAuthToken(registeredCustomer.getUsername(), registeredCustomer.getPassword()));
    }

    private void mergeAnonymousCartWithRegisteredCustomerCartAndDeleteAnonymousUser(Customer anonymousCustomer, 
                                                                                    Customer registeredCustomer) {
        if(Objects.nonNull(anonymousCustomer)) {
            Cart anonymousUserCart = anonymousCustomer.getCart();
            try {
                cartService.mergeCart(registeredCustomer, anonymousUserCart.getCartEntryList()
                        .stream()
                        .map(cartEntry -> new UpdateCartRequest(cartEntry.getProductId(), cartEntry.getQuantity()))
                        .collect(Collectors.toList()));
                
                customerRepository.delete(anonymousCustomer);
            }
            catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }
    }

    public LoginResponse login(String username, String password, String anonymousCartUsername) throws LoginException {
        Customer customer = customerRepository.findByUsername(username);
        if(Objects.isNull(customer)) {
            throw new LoginException("Invalid: username invalid");
        }
        if(!BCrypt.checkpw(password, customer.getPassword())) {
            throw new LoginException("Invalid: password invalid");
        }
        Customer anonymousUser = customerRepository.findByUsername(anonymousCartUsername);
        mergeAnonymousCartWithRegisteredCustomerCartAndDeleteAnonymousUser(anonymousUser, customer);
        return new LoginResponse(customer,generateNewAuthToken(customer.getUsername(), customer.getPassword()));
    }

    private String generateNewAuthToken(String username, String password) {
        return Base64.getEncoder().encodeToString((username+":"+ password).getBytes());
    }

    private void checkForDuplicateUsername(String username) throws CustomerSignupDatabaseException{
        if(customerRepository.findByUsername(username) != null) 
            throw new CustomerSignupDatabaseException("username already exists");
    }

    private void checkForDuplicateEmail(String email) throws CustomerSignupDatabaseException{
        if(customerRepository.findByEmail(email) != null) 
            throw new CustomerSignupDatabaseException("email already exists");
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
