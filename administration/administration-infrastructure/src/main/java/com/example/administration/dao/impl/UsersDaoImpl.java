package com.example.administration.dao.impl;

import com.example.administration.dao.UsersDao;
import com.example.administration.dao.dto.response.UsersOutDTO;
import com.example.core.utils.http.component.HttpClient;
import com.example.core.utils.http.model.HttpRequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
class UsersDaoImpl implements UsersDao {

    private static final String USERS_SERVICE_NAME = "users";
    private static final String USERS_PATH = "/users";

    private final HttpClient httpClient;

    @Autowired
    public UsersDaoImpl(HttpClient client) {
        this.httpClient = client;
    }

    @Override
    public Mono<UsersOutDTO> getUsers() {

        final HttpRequestInfo httpRequestInfo = HttpRequestInfo.builder()
                .host(USERS_SERVICE_NAME)
                .path(USERS_PATH)
                .build();
        return httpClient.get(httpRequestInfo, UsersOutDTO.class);
    }
}