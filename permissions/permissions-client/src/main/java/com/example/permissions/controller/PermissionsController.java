package com.example.permissions.controller;

import com.example.core.utils.mapping.MapperComponent;
import com.example.permissions.dto.request.CreateUserRoleDomainInDTO;
import com.example.permissions.dto.request.CreateUserRoleRequest;
import com.example.permissions.dto.request.DeleteUserRoleDomainInDTO;
import com.example.permissions.dto.request.GetUserRoleDomainInDTO;
import com.example.permissions.dto.response.DeleteUserRoleDomainOutDTO;
import com.example.permissions.dto.response.DeleteUserRolePayload;
import com.example.permissions.dto.response.UserRoleDomainOutDTO;
import com.example.permissions.dto.response.UserRolePayload;
import com.example.permissions.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class PermissionsController {

    private final UserRoleService userRoleService;

    private final MapperComponent mapper;

    @Autowired
    public PermissionsController(UserRoleService userRoleService, MapperComponent mapper) {
        this.userRoleService = userRoleService;
        this.mapper = mapper;
    }

    @PostMapping(value = "/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserRolePayload> createUserRole(@RequestBody @Valid final CreateUserRoleRequest createUserRoleRequest) {
        final CreateUserRoleDomainInDTO createUserRoleRequestDTO = mapper.map(createUserRoleRequest, CreateUserRoleDomainInDTO.class);
        final Mono<UserRoleDomainOutDTO> userRoleResponse = userRoleService.createRole(createUserRoleRequestDTO);
        return userRoleResponse.map(userRole -> mapper.map(userRole, UserRolePayload.class));
    }

    @GetMapping(value = "/roles/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserRolePayload> getUserRole(@PathVariable final String userId) {
        final GetUserRoleDomainInDTO getUserRoleRequestDTO = new GetUserRoleDomainInDTO(userId);
        final Mono<UserRoleDomainOutDTO> userRoleResponse = userRoleService.getUserRole(getUserRoleRequestDTO);
        return userRoleResponse.map(userRole -> mapper.map(userRole, UserRolePayload.class));
    }

    @DeleteMapping(value = "/roles/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<DeleteUserRolePayload> deleteUserRole(@PathVariable final String userId) {
        final DeleteUserRoleDomainInDTO deleteUserRoleRequestDTO = new DeleteUserRoleDomainInDTO(userId);
        final Mono<DeleteUserRoleDomainOutDTO> deleteUserRoleResponse = userRoleService.deleteUserRole(deleteUserRoleRequestDTO);
        return deleteUserRoleResponse.map(res -> mapper.map(res, DeleteUserRolePayload.class));
    }
}
