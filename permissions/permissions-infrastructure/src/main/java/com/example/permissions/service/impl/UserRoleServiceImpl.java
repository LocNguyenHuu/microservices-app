package com.example.permissions.service.impl;

import com.example.core.utils.http.errors.BadRequestException;
import com.example.core.utils.mapping.MapperComponent;
import com.example.permissions.dao.UserRoleDao;
import com.example.permissions.dto.request.CreateUserRoleDomainInDTO;
import com.example.permissions.dto.request.DeleteUserRoleDomainInDTO;
import com.example.permissions.dto.request.GetUserRoleDomainInDTO;
import com.example.permissions.dto.response.DeleteUserRoleDomainOutDTO;
import com.example.permissions.dto.response.UserRoleDomainOutDTO;
import com.example.permissions.entity.UserRole;
import com.example.permissions.entity.model.UserRoleEnum;
import com.example.permissions.service.UserRoleService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.permissions.constant.Errors.INVALID_ROLE_ERROR_CODE;
import static com.example.permissions.constant.Errors.INVALID_ROLE_ERROR_MESSAGE;
import static com.example.permissions.constant.Errors.USER_ROLE_NOT_FOUND_ERROR_CODE;
import static com.example.permissions.constant.Errors.USER_ROLE_NOT_FOUND_ERROR_MESSAGE;

@Service
class UserRoleServiceImpl implements UserRoleService {

    private static final String USER_ROLE_DELETED_MESSAGE = "User role deleted";

    private final UserRoleDao userRoleDao;

    @Setter
    private MapperComponent mapper;

    @Autowired
    public UserRoleServiceImpl(UserRoleDao userRoleDao, MapperComponent mapper) {
        this.userRoleDao = userRoleDao;
        this.mapper = mapper;
    }

    @Override
    public Mono<UserRoleDomainOutDTO> createRole(CreateUserRoleDomainInDTO createUserRoleRequest) {
        if (!UserRoleEnum.contains(createUserRoleRequest.getRole())) {
            return Mono.error(new BadRequestException(INVALID_ROLE_ERROR_CODE, INVALID_ROLE_ERROR_MESSAGE));
        }
        final Mono<UserRole> userRoleResponse = userRoleDao.save(mapper.map(createUserRoleRequest, UserRole.class));
        return userRoleResponse.map(userRole -> new UserRoleDomainOutDTO(userRole.getUserId(), userRole.getRole().name()));
    }

    @Override
    public Mono<UserRoleDomainOutDTO> getUserRole(final GetUserRoleDomainInDTO getUserRoleRequest) {
        final Mono<UserRole> userRoleResponse = userRoleDao.findOneByUserId(getUserRoleRequest.getUserId());
        return userRoleResponse.flatMap(userRole ->
                userRole != null ?
                        Mono.just(new UserRoleDomainOutDTO(userRole.getUserId(), userRole.getRole().name()))
                        : Mono.error(new BadRequestException(USER_ROLE_NOT_FOUND_ERROR_CODE, USER_ROLE_NOT_FOUND_ERROR_MESSAGE))
        );
    }

    @Override
    public Mono<DeleteUserRoleDomainOutDTO> deleteUserRole(DeleteUserRoleDomainInDTO deleteUserRoleRequest) {
        final String userId = deleteUserRoleRequest.getUserId();
        final Mono<UserRole> deleteUserRoleResponse = userRoleDao.findById(userId)
                .flatMap(oldValue ->
                        userRoleDao.deleteById(userId)
                                .then(Mono.just(oldValue))
                ).single();
        return deleteUserRoleResponse.map(res -> new DeleteUserRoleDomainOutDTO(USER_ROLE_DELETED_MESSAGE));
    }
}
