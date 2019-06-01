package com.example.signup.dao.impl;

import com.example.core.utils.http.component.HttpClient;
import com.example.core.utils.http.model.HttpRequestInfo;
import com.example.signup.dao.PasswordManagerDao;
import com.example.signup.dao.dto.request.NewUserPasswordInDTO;
import com.example.signup.dao.dto.response.NewUserPasswordOutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
class PasswordManagerDaoImpl implements PasswordManagerDao {

    private static final String PASSWORD_SERVICE_NAME = "password-manager";
    private static final String PASSWORDS_PATH = "/passwords";

    private final HttpClient httpClient;

    @Autowired
    public PasswordManagerDaoImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Mono<NewUserPasswordOutDTO> createPassword(final NewUserPasswordInDTO createPasswordRequest) {
        final HttpRequestInfo httpRequestInfo = HttpRequestInfo.builder()
                .host(PASSWORD_SERVICE_NAME)
                .path(PASSWORDS_PATH)
                .body(createPasswordRequest)
                .build();
        return httpClient.post(httpRequestInfo, NewUserPasswordOutDTO.class);
    }

}