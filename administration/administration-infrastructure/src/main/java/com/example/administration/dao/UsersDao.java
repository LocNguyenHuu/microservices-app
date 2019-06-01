package com.example.administration.dao;

import com.example.administration.dao.dto.response.UsersOutDTO;
import reactor.core.publisher.Mono;

public interface UsersDao {

    Mono<UsersOutDTO> getUsers();

}