package com.example.core.utils.http.errors;

public class InternalErrorException extends ServiceException {

    private static final String INTERNAL_ERROR_CODE = "internal.error";

    private static final String INTERNAL_ERROR_MESSAGE = "Internal error, try again in a few minutes";


    public InternalErrorException() {
        super(INTERNAL_ERROR_CODE, INTERNAL_ERROR_MESSAGE);
    }

    public InternalErrorException(String message) {
        super(INTERNAL_ERROR_CODE, message);
    }
}
