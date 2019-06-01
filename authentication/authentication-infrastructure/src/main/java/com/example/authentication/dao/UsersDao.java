package com.example.authentication.dao;

import com.example.authentication.dao.dto.request.FindUserByEmailInDTO;
import com.example.authentication.dao.dto.response.FindUserByEmailOutDTO;
import reactor.core.publisher.Mono;

public interface UsersDao {

    Mono<FindUserByEmailOutDTO> findUserByEmail(final FindUserByEmailInDTO findUserByEmailInDTO);

}
