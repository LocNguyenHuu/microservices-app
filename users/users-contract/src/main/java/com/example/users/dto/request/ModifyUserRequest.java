package com.example.users.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class ModifyUserRequest {

    @Email
    private String email;

    private String name;

    @Pattern(regexp = "^[\\s+0-9]+$")
    private String phone;

}
