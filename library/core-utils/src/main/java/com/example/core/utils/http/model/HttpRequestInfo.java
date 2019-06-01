package com.example.core.utils.http.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Builder
@Data
public class HttpRequestInfo {

    @Builder.Default
    private String protocol = "http";

    private String host;

    private String path;

    private HashMap<String, String> queryParameters;

    private Object body;

}