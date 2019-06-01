package com.example.signup.dao.impl;

import com.example.core.utils.http.component.HttpClient;
import com.example.core.utils.http.model.HttpRequestInfo;
import com.example.signup.dao.UsersDao;
import com.example.signup.dao.dto.request.NewUserInDTO;
import com.example.signup.dao.dto.response.NewUserOutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
class UsersDaoImpl implements UsersDao {

    private static final String USERS_SERVICE_NAME = "users";
    private static final String USERS_PATH = "/users";

    private final HttpClient httpClient;

    @Autowired
    public UsersDaoImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Mono<NewUserOutDTO> createUser(final NewUserInDTO createUserRequest) {
        final HttpRequestInfo httpRequestInfo = HttpRequestInfo.builder()
                .host(USERS_SERVICE_NAME)
                .path(USERS_PATH)
                .body(createUserRequest)
                .build();
        return httpClient.post(httpRequestInfo, NewUserOutDTO.class);
    }

}