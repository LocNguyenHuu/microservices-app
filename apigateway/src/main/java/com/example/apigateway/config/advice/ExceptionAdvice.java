package com.example.apigateway.config.advice;

import com.example.core.utils.http.errors.ServiceError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ControllerAdvice
public class ExceptionAdvice {

    private static final String INVALID_TOKEN_ERROR_CODE = "invalid.token";
    private static final String INVALID_TOKEN_ERROR_MESSAGE = "Invalid token";

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ServiceError> handleBadRequest(final BadCredentialsException ex) {
        return new ResponseEntity<>(new ServiceError(INVALID_TOKEN_ERROR_CODE, INVALID_TOKEN_ERROR_MESSAGE), HttpStatus.BAD_REQUEST);

    }

}
