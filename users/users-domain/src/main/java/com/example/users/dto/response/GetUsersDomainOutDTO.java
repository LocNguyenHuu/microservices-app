package com.example.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class GetUsersDomainOutDTO {

    private List<UserDomainOutDTO> users;

}
