package com.example.authentication.dao.impl;

import com.example.authentication.dao.UsersDao;
import com.example.authentication.dao.dto.request.FindUserByEmailInDTO;
import com.example.authentication.dao.dto.response.FindUserByEmailOutDTO;
import com.example.core.utils.http.component.HttpClient;
import com.example.core.utils.http.model.HttpRequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
class UsersDaoImpl implements UsersDao {

    private final HttpClient httpClient;

    private static final String USERS_SERVICE_NAME = "users";
    private static final String USERS_PATH = "/users/email/";

    @Autowired
    public UsersDaoImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Mono<FindUserByEmailOutDTO> findUserByEmail(final FindUserByEmailInDTO userEmailDTO) {
        final HttpRequestInfo httpRequestInfo = HttpRequestInfo.builder()
                .host(USERS_SERVICE_NAME)
                .path(USERS_PATH + userEmailDTO.getEmail())
                .build();
        return httpClient.get(httpRequestInfo, FindUserByEmailOutDTO.class);
    }
}
