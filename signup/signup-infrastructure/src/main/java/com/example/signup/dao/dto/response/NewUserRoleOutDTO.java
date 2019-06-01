package com.example.signup.dao.dto.response;


import com.example.signup.dao.dto.model.UserRoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewUserRoleOutDTO {

    private String userId;

    private UserRoleEnum role;

}
