package com.example.core.utils.http.errors;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public abstract class ServiceException extends RuntimeException {

    @Getter
    @Setter
    @NonNull
    private String code;

    @Getter
    @Setter
    @NonNull
    private String message;

    @Getter
    private long timestamp = System.currentTimeMillis();

}
