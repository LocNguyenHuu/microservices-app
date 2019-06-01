package com.example.signup.dao.dto.request;

import com.example.signup.dao.dto.model.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRoleInDTO {

    private String userId;

    private UserRoleEnum role;

}
