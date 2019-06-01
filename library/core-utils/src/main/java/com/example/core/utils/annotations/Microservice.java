package com.example.core.utils.annotations;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@ComponentScan({"com.example.core.http.advice", "com.example.core.http.component", "com.example.core.mapping.*"})
public @interface Microservice {
}