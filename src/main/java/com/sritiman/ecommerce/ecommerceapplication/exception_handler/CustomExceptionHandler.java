package com.sritiman.ecommerce.ecommerceapplication.exception_handler;

import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomerNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.CustomerSignupDatabaseException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.LoginException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.OrderNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.payments.AccountNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.payments.InsufficientBalanceException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.payments.UnsupportedPaymentModeException;
import com.sritiman.ecommerce.ecommerceapplication.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomerSignupDatabaseException.class)
    public ResponseEntity<ErrorResponse> databaseException(CustomerSignupDatabaseException customerSignupDatabaseException) {
        return new ResponseEntity<>(new ErrorResponse(customerSignupDatabaseException.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> loginException(LoginException loginException) {
        return new ResponseEntity<>(new ErrorResponse(loginException.getMessage(), null), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> accountNotFoundError(AccountNotFoundException accountNotFoundException) {
        return new ResponseEntity<>(new ErrorResponse(accountNotFoundException.getMessage(), accountNotFoundException.getDetails()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> insufficientBalanceError(InsufficientBalanceException insufficientBalanceException) {
        return new ResponseEntity<>(new ErrorResponse(insufficientBalanceException.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedPaymentModeException.class)
    public ResponseEntity<ErrorResponse> unsupportedPaymentModeError(UnsupportedPaymentModeException unsupportedPaymentModeException) {
        return new ResponseEntity<>(new ErrorResponse(unsupportedPaymentModeException.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> customerNotFoundError(CustomerNotFoundException customerNotFoundException) {
        return new ResponseEntity<>(new ErrorResponse(customerNotFoundException.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> orderNotFoundError(OrderNotFoundException orderNotFoundException) {
        return new ResponseEntity<>(new ErrorResponse(orderNotFoundException.getMessage(), null), HttpStatus.NOT_FOUND);
    }

}
