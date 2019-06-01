package com.example.permissions.service;

import com.example.permissions.dto.request.CreateUserRoleDomainInDTO;
import com.example.permissions.dto.request.DeleteUserRoleDomainInDTO;
import com.example.permissions.dto.request.GetUserRoleDomainInDTO;
import com.example.permissions.dto.response.DeleteUserRoleDomainOutDTO;
import com.example.permissions.dto.response.UserRoleDomainOutDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface UserRoleService {

    Mono<UserRoleDomainOutDTO> createRole(final CreateUserRoleDomainInDTO createUserRoleRequest);

    Mono<UserRoleDomainOutDTO> getUserRole(final GetUserRoleDomainInDTO getUserRoleRequest);

    Mono<DeleteUserRoleDomainOutDTO> deleteUserRole(final DeleteUserRoleDomainInDTO deleteUserRoleRequest);

}
