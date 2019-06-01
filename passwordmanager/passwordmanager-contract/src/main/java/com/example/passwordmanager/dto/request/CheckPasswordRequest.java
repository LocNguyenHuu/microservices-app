package com.example.passwordmanager.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CheckPasswordRequest {

    @NotEmpty
    private String password;

}
