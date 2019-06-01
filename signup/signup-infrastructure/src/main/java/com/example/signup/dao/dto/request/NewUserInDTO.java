package com.example.signup.dao.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserInDTO {

    private String email;

    private String name;

    private String phone;

}
