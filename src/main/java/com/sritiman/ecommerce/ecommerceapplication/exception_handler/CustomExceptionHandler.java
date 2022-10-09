package com.sritiman.ecommerce.ecommerceapplication.exception_handler;

import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomerSignupDatabaseException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.LoginException;
import com.sritiman.ecommerce.ecommerceapplication.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomerSignupDatabaseException.class)
    public ResponseEntity<ErrorResponse> databaseException(CustomerSignupDatabaseException customerSignupDatabaseException) {
        return new ResponseEntity<>(new ErrorResponse(customerSignupDatabaseException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> loginException(LoginException loginException) {
        return new ResponseEntity<>(new ErrorResponse(loginException.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
