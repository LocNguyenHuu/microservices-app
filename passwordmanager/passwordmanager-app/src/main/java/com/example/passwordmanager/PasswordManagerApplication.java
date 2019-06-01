package com.example.passwordmanager;

import com.example.core.utils.BaseMicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
        basePackages = {
                "com.example.passwordmanager.config",
                "com.example.passwordmanager.controller",
                "com.example.passwordmanager.service",
                "com.example.passwordmanager.dao"
        })
public class PasswordManagerApplication extends BaseMicroService {

    public static void main(String[] args) {
        SpringApplication.run(PasswordManagerApplication.class, args);
    }

}
