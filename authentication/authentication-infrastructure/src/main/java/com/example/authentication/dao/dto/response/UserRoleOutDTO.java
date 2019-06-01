package com.example.authentication.dao.dto.response;

import com.example.authentication.dao.dto.response.model.UserRoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRoleOutDTO {

    private String userId;

    private UserRoleEnum role;

}

