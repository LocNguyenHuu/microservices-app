package com.example.signup;

import com.example.core.utils.BaseMicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
        basePackages = {
                "com.example.signup.controller",
                "com.example.signup.service",
                "com.example.signup.dao"
        })
public class SignUpApplication extends BaseMicroService {

    public static void main(String[] args) {
        SpringApplication.run(SignUpApplication.class, args);
    }
}

