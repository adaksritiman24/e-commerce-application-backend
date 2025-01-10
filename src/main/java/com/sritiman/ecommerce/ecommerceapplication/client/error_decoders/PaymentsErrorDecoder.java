package com.sritiman.ecommerce.ecommerceapplication.client.error_decoders;

import com.sritiman.ecommerce.ecommerceapplication.exceptions.payments.AccountNotFoundException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.payments.InsufficientBalanceException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.payments.PaymentsServiceException;
import com.sritiman.ecommerce.ecommerceapplication.exceptions.payments.UnsupportedPaymentModeException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import static com.sritiman.ecommerce.ecommerceapplication.constants.Constants.*;

public class PaymentsErrorDecoder implements ErrorDecoder {
    Logger LOG = LoggerFactory.getLogger(PaymentsErrorDecoder.class);
    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        String responseBody = "";
        try {
            Reader reader = response.body().asReader(StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);

            responseBody = br.readLine();

        } catch (Exception exception) {
            LOG.error("Some error occurred while decoding the error message: {}", exception.getMessage());
        }
        LOG.error("Error occurred while calling payments Service with status: {}", status);

        if(responseBody.contains(PAYMENT_MODE_NOT_SUPPORTED)) {
            throw new UnsupportedPaymentModeException(PAYMENT_MODE_NOT_SUPPORTED);
        }
        else if (responseBody.contains(ACCOUNT_NOT_FOUND)) {
            throw new AccountNotFoundException(ACCOUNT_NOT_FOUND,"");
        }
        else if (responseBody.contains(ACCOUNT_BALANCE_INSUFFICIENT)) {
            throw new InsufficientBalanceException(ACCOUNT_BALANCE_INSUFFICIENT);
        }
        else {
            throw new PaymentsServiceException("Some error occurred at Payments service");
        }
    }
}
