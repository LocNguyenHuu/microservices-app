package com.example.signup.dao;

import com.example.signup.dao.dto.request.NewUserRoleInDTO;
import com.example.signup.dao.dto.response.NewUserRoleOutDTO;
import reactor.core.publisher.Mono;

public interface PermissionsDao {

    Mono<NewUserRoleOutDTO> createUserRole(final NewUserRoleInDTO createUserRoleRequest);

}
