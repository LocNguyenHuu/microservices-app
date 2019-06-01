package com.example.userprofile.dto.request;

import lombok.Data;

@Data
public class ModifyUserPasswordDomainInDTO {

    private String userId;

    private String password;

    private String oldPassword;

}
