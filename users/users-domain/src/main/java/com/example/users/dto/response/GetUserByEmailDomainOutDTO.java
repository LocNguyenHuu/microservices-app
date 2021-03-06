package com.example.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class GetUserByEmailDomainOutDTO {

    private String id;

    private String email;

}
