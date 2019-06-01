package com.example.users.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GetUserByIdDomainOutDTO {

    private String id;

    private String email;

    private String name;

    private String phone;

}
