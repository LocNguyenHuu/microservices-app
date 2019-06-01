package com.example.users.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UsersPayload {

    private List<UserPayload> users;

}
