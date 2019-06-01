package com.example.authentication;

import com.example.core.utils.BaseMicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
        basePackages = {
                "com.example.authentication.controller",
                "com.example.authentication.service",
                "com.example.authentication.dao"
        })
public class AuthenticationApplication extends BaseMicroService {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }

}
