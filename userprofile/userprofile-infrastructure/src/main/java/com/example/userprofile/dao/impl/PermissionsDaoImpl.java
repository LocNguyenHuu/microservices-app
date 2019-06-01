package com.example.userprofile.dao.impl;

import com.example.core.utils.http.component.HttpClient;
import com.example.core.utils.http.model.HttpRequestInfo;
import com.example.userprofile.dao.PermissionsDao;
import com.example.userprofile.dao.dto.request.DeleteUserRoleInDTO;
import com.example.userprofile.dao.dto.response.DeleteUserRoleOutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
class PermissionsDaoImpl implements PermissionsDao {

    private static final String PERMISSIONS_SERVICE_NAME = "permissions";
    private static final String USER_ROLE_PATH = "/roles/user/";

    private final HttpClient httpClient;

    @Autowired
    public PermissionsDaoImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Mono<DeleteUserRoleOutDTO> deleteUserRole(final DeleteUserRoleInDTO deleteUserRoleRequest) {
        final HttpRequestInfo httpRequestInfo = HttpRequestInfo.builder()
                .host(PERMISSIONS_SERVICE_NAME)
                .path(USER_ROLE_PATH + deleteUserRoleRequest.getUserId()).build();
        return httpClient.delete(httpRequestInfo, DeleteUserRoleOutDTO.class);
    }

}
