package com.example.users.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ModifyUserDomainInDTO {

    private String id;

    private String email;

    private String name;

    private String phone;

}
