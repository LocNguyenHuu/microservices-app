package com.example.administration.dto.response;

import com.example.administration.dto.response.model.UserSubPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersPayload {

    private List<UserSubPayload> users;

}
