package com.example.signup.dao;

import com.example.signup.dao.dto.request.NewUserInDTO;
import com.example.signup.dao.dto.response.NewUserOutDTO;
import reactor.core.publisher.Mono;

public interface UsersDao {

    Mono<NewUserOutDTO> createUser(final NewUserInDTO createUserRequest);

}