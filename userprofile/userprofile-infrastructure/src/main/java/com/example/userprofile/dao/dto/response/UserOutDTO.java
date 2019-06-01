package com.example.userprofile.dao.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserOutDTO {

    private String id;

    private String email;

    private String name;

    private String phone;

}
