package com.example.userprofile.dao;

import com.example.userprofile.dao.dto.request.DeleteUserRoleInDTO;
import com.example.userprofile.dao.dto.response.DeleteUserRoleOutDTO;
import reactor.core.publisher.Mono;

public interface PermissionsDao {

    Mono<DeleteUserRoleOutDTO> deleteUserRole(final DeleteUserRoleInDTO deleteUserRoleRequest);

}
