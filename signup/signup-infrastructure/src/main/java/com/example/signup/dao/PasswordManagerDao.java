package com.example.signup.dao;

import com.example.signup.dao.dto.request.NewUserPasswordInDTO;
import com.example.signup.dao.dto.response.NewUserPasswordOutDTO;
import reactor.core.publisher.Mono;

public interface PasswordManagerDao {

    Mono<NewUserPasswordOutDTO> createPassword(final NewUserPasswordInDTO createPasswordRequest);

}
