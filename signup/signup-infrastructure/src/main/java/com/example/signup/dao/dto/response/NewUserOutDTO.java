package com.example.signup.dao.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserOutDTO {

    private String id;

    private String email;

    private String name;

    private String phone;

}
