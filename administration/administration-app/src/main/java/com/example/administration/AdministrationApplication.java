package com.example.administration;

import com.example.core.utils.BaseMicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
        basePackages = {
                "com.example.administration.mapper",
                "com.example.administration.controller",
                "com.example.administration.service",
                "com.example.administration.dao"
        })
public class AdministrationApplication extends BaseMicroService {

    public static void main(String[] args) {
        SpringApplication.run(AdministrationApplication.class, args);
    }

}

