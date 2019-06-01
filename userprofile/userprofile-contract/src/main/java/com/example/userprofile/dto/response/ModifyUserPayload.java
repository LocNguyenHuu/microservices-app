package com.example.userprofile.dto.response;

import lombok.Data;

@Data
public class ModifyUserPayload {

    private String id;

    private String email;

    private String name;

    private String phone;

}
