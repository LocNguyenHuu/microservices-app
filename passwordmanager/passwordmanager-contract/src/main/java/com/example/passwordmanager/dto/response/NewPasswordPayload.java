package com.example.passwordmanager.dto.response;

import lombok.Data;

@Data
public class NewPasswordPayload {

    private String userId;

    private String password;

}
