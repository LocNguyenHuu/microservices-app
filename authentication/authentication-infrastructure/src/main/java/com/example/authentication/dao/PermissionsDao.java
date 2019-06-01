package com.example.authentication.dao;

import com.example.authentication.dao.dto.request.UserRoleInDTO;
import com.example.authentication.dao.dto.response.UserRoleOutDTO;
import reactor.core.publisher.Mono;

public interface PermissionsDao {

    Mono<UserRoleOutDTO> getUserRole(final UserRoleInDTO userRoleInDTO);

}
