package com.example.core.utils;

import com.example.core.utils.annotations.Microservice;
import com.example.core.utils.http.advice.ExceptionAdvice;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@Microservice
@SpringBootApplication
public class BaseMicroService {

    @Bean
    ExceptionAdvice exceptionAdvice() {
        return new ExceptionAdvice();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

}
