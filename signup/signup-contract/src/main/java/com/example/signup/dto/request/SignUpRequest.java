package com.example.signup.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class SignUpRequest {

    @Email
    @NotEmpty
    private String email;

    @Pattern(regexp = "^[A-Za-z0-9]+$")
    @Length(min = 5)
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    @Pattern(regexp = "^[\\s+0-9]+$")
    private String phone;

}
