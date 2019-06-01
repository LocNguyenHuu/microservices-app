package com.example.userprofile.dao.impl;

import com.example.core.utils.http.component.HttpClient;
import com.example.core.utils.http.model.HttpRequestInfo;
import com.example.userprofile.dao.UsersDao;
import com.example.userprofile.dao.dto.request.GetUserInDTO;
import com.example.userprofile.dao.dto.request.ModifyUserInDTO;
import com.example.userprofile.dao.dto.response.DeleteUserOutDTO;
import com.example.userprofile.dao.dto.response.ModifyUserOutDTO;
import com.example.userprofile.dao.dto.response.UserOutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
class UsersDaoImpl implements UsersDao {

    private static final String USERS_SERVICE_NAME = "users";
    private static final String USERS_PATH = "/users/";

    private final HttpClient httpClient;

    @Autowired
    public UsersDaoImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Mono<UserOutDTO> getUser(final GetUserInDTO getUserRequest) {
        final HttpRequestInfo httpRequestInfo = HttpRequestInfo.builder()
                .host(USERS_SERVICE_NAME)
                .path(USERS_PATH + getUserRequest.getUserId()).build();
        return httpClient.get(httpRequestInfo, UserOutDTO.class);
    }

    @Override
    public Mono<ModifyUserOutDTO> modifyUser(final String userId, final ModifyUserInDTO modifyUserRequest) {
        final HttpRequestInfo httpRequestInfo = HttpRequestInfo.builder()
                .host(USERS_SERVICE_NAME)
                .path(USERS_PATH + userId)
                .body(modifyUserRequest)
                .build();
        return httpClient.patch(httpRequestInfo, ModifyUserOutDTO.class);
    }

    @Override
    public Mono<DeleteUserOutDTO> deleteUser(final String userId) {
        final HttpRequestInfo httpRequestInfo = HttpRequestInfo.builder()
                .host(USERS_SERVICE_NAME)
                .path(USERS_PATH + userId)
                .build();
        return httpClient.delete(httpRequestInfo, DeleteUserOutDTO.class);
    }

}
