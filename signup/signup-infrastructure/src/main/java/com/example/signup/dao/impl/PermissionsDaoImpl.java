package com.example.signup.dao.impl;

import com.example.core.utils.http.component.HttpClient;
import com.example.core.utils.http.model.HttpRequestInfo;
import com.example.signup.dao.PermissionsDao;
import com.example.signup.dao.dto.request.NewUserRoleInDTO;
import com.example.signup.dao.dto.response.NewUserRoleOutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
class PermissionsDaoImpl implements PermissionsDao {

    private static final String PERMISSIONS_SERVICE_NAME = "permissions";
    private static final String ROLES_PATH = "/roles";

    private final HttpClient httpClient;

    @Autowired
    public PermissionsDaoImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Mono<NewUserRoleOutDTO> createUserRole(final NewUserRoleInDTO createUserRoleRequest) {
        final HttpRequestInfo httpRequestInfo = HttpRequestInfo.builder()
                .host(PERMISSIONS_SERVICE_NAME)
                .path(ROLES_PATH)
                .body(createUserRoleRequest)
                .build();
        return httpClient.post(httpRequestInfo, NewUserRoleOutDTO.class);
    }
}