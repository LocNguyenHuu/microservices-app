package com.example.administration.dto.response;

import com.example.administration.dto.response.model.UserSubDomainOutDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UsersDomainOutDTO {

    private List<UserSubDomainOutDTO> users;

}
