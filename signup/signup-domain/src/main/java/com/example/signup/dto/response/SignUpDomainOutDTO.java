package com.example.signup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDomainOutDTO {

    private String id;

    private String email;

    private String name;

    private String phone;

}
