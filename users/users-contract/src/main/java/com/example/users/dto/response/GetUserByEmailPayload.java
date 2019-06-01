package com.example.users.dto.response;

import lombok.Data;

@Data
public class GetUserByEmailPayload {

    private String id;

    private String email;

}
