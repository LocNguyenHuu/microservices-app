package com.example.signup.dto.request;

import lombok.Data;

@Data
public class SignUpDomainInDTO {
    private String email;

    private String password;

    private String name;

    private String phone;
}
