package com.example.userprofile.dao.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ModifyUserInDTO {

    private String email;

    private String name;

    private String phone;

}
