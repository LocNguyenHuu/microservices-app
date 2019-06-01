package com.example.users.dto.response;

import lombok.Data;

@Data
public class NewUserPayload {

    private String id;

    private String email;

    private String name;

    private String phone;

}
