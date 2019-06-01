package com.example.authentication.dao.impl;

import com.example.authentication.dao.PermissionsDao;
import com.example.authentication.dao.dto.request.UserRoleInDTO;
import com.example.authentication.dao.dto.response.UserRoleOutDTO;
import com.example.core.utils.http.component.HttpClient;
import com.example.core.utils.http.model.HttpRequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
class PermissionsDaoImpl implements PermissionsDao {

    private static final String PERMISSIONS_SERVICE_NAME = "permissions";
    private static final String USER_ROLE_PATH = "roles/user/";

    private final HttpClient httpClient;

    @Autowired
    public PermissionsDaoImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Mono<UserRoleOutDTO> getUserRole(UserRoleInDTO userRoleDTO) {
        final HttpRequestInfo httpRequestInfo = HttpRequestInfo.builder()
                .host(PERMISSIONS_SERVICE_NAME)
                .path(USER_ROLE_PATH + userRoleDTO.getUserId())
                .build();
        return httpClient.get(httpRequestInfo, UserRoleOutDTO.class);
    }
}
