package com.example.authentication.dao;

import com.example.authentication.dao.dto.request.CheckUserPasswordInDTO;
import com.example.authentication.dao.dto.response.CheckUserPasswordOutDTO;
import reactor.core.publisher.Mono;

public interface PasswordManagerDao {

    Mono<CheckUserPasswordOutDTO> checkUserPassword(final String userId, final CheckUserPasswordInDTO checkUserPasswordInDTO);

}
