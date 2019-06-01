package com.example.core.utils.http.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ServiceError implements Serializable {

    @NonNull
    private String code;

    @NonNull
    private String message;

    private long timestamp = System.currentTimeMillis();

    private Object details;

}
