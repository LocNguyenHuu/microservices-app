package com.example.signup.dao.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserPasswordInDTO {

    private String userId;

    private String password;

}
