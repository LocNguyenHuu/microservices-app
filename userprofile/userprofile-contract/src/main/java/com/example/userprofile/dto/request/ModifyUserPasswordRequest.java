package com.example.userprofile.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class ModifyUserPasswordRequest {

    @Pattern(regexp = "^[A-Za-z0-9]+$")
    @Length(min = 5)
    private String password;

    @NotEmpty
    private String oldPassword;

}
