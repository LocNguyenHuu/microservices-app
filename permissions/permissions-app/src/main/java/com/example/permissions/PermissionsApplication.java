package com.example.permissions;

import com.example.core.utils.BaseMicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
        basePackages = {
                "com.example.permissions.config",
                "com.example.permissions.controller",
                "com.example.permissions.service",
                "com.example.permissions.dao"
        })
public class PermissionsApplication extends BaseMicroService {

    public static void main(String[] args) {
        SpringApplication.run(PermissionsApplication.class, args);
    }

}

