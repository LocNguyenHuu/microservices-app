package com.example.authentication.dao.impl;

import com.example.authentication.dao.PasswordManagerDao;
import com.example.authentication.dao.dto.request.CheckUserPasswordInDTO;
import com.example.authentication.dao.dto.response.CheckUserPasswordOutDTO;
import com.example.core.utils.http.component.HttpClient;
import com.example.core.utils.http.model.HttpRequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
class PasswordManagerDaoImpl implements PasswordManagerDao {

    private static final String PASSWORDS_SERVICE_NAME = "password-manager";
    private static final String USER_PASSWORD_PATH = "passwords/user/";

    private final HttpClient httpClient;

    @Autowired
    public PasswordManagerDaoImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Mono<CheckUserPasswordOutDTO> checkUserPassword(final String userId, CheckUserPasswordInDTO userPasswordDTO) {
        final HttpRequestInfo httpRequestInfo = HttpRequestInfo.builder()
                .host(PASSWORDS_SERVICE_NAME)
                .path(USER_PASSWORD_PATH + userId)
                .body(userPasswordDTO)
                .build();
        return httpClient.post(httpRequestInfo, CheckUserPasswordOutDTO.class);
    }
}
