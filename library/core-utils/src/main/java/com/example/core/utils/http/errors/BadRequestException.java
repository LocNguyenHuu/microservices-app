package com.example.core.utils.http.errors;

public class BadRequestException extends ServiceException {
    public BadRequestException(String code, String message) {
        super(code, message);
    }
}
