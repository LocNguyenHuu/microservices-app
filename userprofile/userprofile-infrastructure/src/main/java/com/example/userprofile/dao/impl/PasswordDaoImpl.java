package com.example.userprofile.dao.impl;

import com.example.core.utils.http.component.HttpClient;
import com.example.core.utils.http.model.HttpRequestInfo;
import com.example.userprofile.dao.PasswordDao;
import com.example.userprofile.dao.dto.request.CheckUserPasswordInDTO;
import com.example.userprofile.dao.dto.request.ModifyUserPasswordInDTO;
import com.example.userprofile.dao.dto.response.CheckUserPasswordOutDTO;
import com.example.userprofile.dao.dto.response.DeleteUserPasswordOutDTO;
import com.example.userprofile.dao.dto.response.ModifyUserPasswordOutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
class PasswordDaoImpl implements PasswordDao {

    private static final String PASSWORD_SERVICE_NAME = "password-manager";
    private static final String USER_PASSWORD_PATH = "/passwords/user/";

    private final HttpClient httpClient;

    @Autowired
    public PasswordDaoImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Mono<ModifyUserPasswordOutDTO> modifyUserPassword(final String userId, final ModifyUserPasswordInDTO modifyUserPasswordRequest) {
        final HttpRequestInfo httpRequestInfo = HttpRequestInfo.builder()
                .body(modifyUserPasswordRequest)
                .host(PASSWORD_SERVICE_NAME)
                .path(USER_PASSWORD_PATH + userId).build();
        return httpClient.put(httpRequestInfo, ModifyUserPasswordOutDTO.class);
    }

    @Override
    public Mono<DeleteUserPasswordOutDTO> deleteUserPassword(final String userId) {
        final HttpRequestInfo httpRequestInfo = HttpRequestInfo.builder()
                .host(PASSWORD_SERVICE_NAME)
                .path(USER_PASSWORD_PATH + userId).build();
        return httpClient.delete(httpRequestInfo, DeleteUserPasswordOutDTO.class);
    }

    @Override
    public Mono<CheckUserPasswordOutDTO> checkUserPassword(final String userId, final CheckUserPasswordInDTO checkUserPasswordRequest) {
        final HttpRequestInfo httpRequestInfo = HttpRequestInfo.builder()
                .host(PASSWORD_SERVICE_NAME)
                .path(USER_PASSWORD_PATH + userId)
                .body(checkUserPasswordRequest)
                .build();
        return httpClient.post(httpRequestInfo, CheckUserPasswordOutDTO.class);
    }

}
