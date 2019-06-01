package com.example.permissions.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateUserRoleRequest {

    @NotEmpty
    private String userId;

    @NotEmpty
    private String role;

}
