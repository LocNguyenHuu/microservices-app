package com.example.signup.dao.dto.response;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewUserPasswordOutDTO {

    private String userId;

    private String password;

}
