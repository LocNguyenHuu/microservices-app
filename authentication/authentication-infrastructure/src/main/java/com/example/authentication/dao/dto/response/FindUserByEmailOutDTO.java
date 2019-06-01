package com.example.authentication.dao.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindUserByEmailOutDTO {

    private String id;

    private String email;

}

