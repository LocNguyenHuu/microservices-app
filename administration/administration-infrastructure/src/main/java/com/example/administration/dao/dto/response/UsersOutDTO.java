package com.example.administration.dao.dto.response;

import com.example.administration.dao.dto.response.model.UserSubOutDTO;
import lombok.Data;

import java.util.List;

@Data
public class UsersOutDTO {

    private List<UserSubOutDTO> users;

}
