package com.example.users;

import com.example.core.utils.BaseMicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
        basePackages = {
                "com.example.users.config",
                "com.example.users.controller",
                "com.example.users.service",
                "com.example.users.dao"
        })
public class UsersApplication extends BaseMicroService {

    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

}
