package com.example.userprofile;

import com.example.core.utils.BaseMicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
        basePackages = {
                "com.example.userprofile.controller",
                "com.example.userprofile.service",
                "com.example.userprofile.dao"
        })
public class UserProfileApplication extends BaseMicroService {

    public static void main(String[] args) {
        SpringApplication.run(UserProfileApplication.class, args);
    }

}

