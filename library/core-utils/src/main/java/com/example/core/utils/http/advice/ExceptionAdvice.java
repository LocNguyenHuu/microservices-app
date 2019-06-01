package com.example.core.utils.http.advice;

import com.example.core.utils.http.errors.BadRequestException;
import com.example.core.utils.http.errors.InputFieldsErrorDetails;
import com.example.core.utils.http.errors.InternalErrorException;
import com.example.core.utils.http.errors.ServiceError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ControllerAdvice
public class ExceptionAdvice {

    private static final String INTERNAL_SERVER_ERROR_CODE = "internal.server.error";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";
    private static final String INVALID_PARAMETERS_EXCEPTION_ERROR_CODE = "invalid.parameters.exception";
    private static final String INVALID_PARAMETERS_EXCEPTION_MESSAGE = "Invalid input parameters";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ServiceError> handleInternalError(final Exception ex) {
        return new ResponseEntity<>(new ServiceError(INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ServiceError> fieldsValidationError(MethodArgumentNotValidException ex) {
        return handleFieldsError(ex.getBindingResult());
    }

    @ExceptionHandler({WebExchangeBindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ServiceError> fieldsValidationError(WebExchangeBindException ex) {
        return handleFieldsError(ex.getBindingResult());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ServiceError> handleBadRequestError(final BadRequestException ex) {
        return new ResponseEntity<>(new ServiceError(ex.getCode(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ServiceError> handleInternalError(final InternalErrorException ex) {
        return new ResponseEntity<>(new ServiceError(ex.getCode(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ServiceError> handleFieldsError(BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.toList());
        ServiceError serviceError = new ServiceError(INVALID_PARAMETERS_EXCEPTION_ERROR_CODE, INVALID_PARAMETERS_EXCEPTION_MESSAGE);
        serviceError.setDetails(new InputFieldsErrorDetails(fields));
        return new ResponseEntity<>(serviceError, HttpStatus.BAD_REQUEST);
    }

}
