package com.example.userprofile.service.impl;

import com.example.core.utils.http.errors.BadRequestException;
import com.example.core.utils.mapping.MapperComponent;
import com.example.userprofile.dao.PasswordDao;
import com.example.userprofile.dao.PermissionsDao;
import com.example.userprofile.dao.UsersDao;
import com.example.userprofile.dao.dto.request.CheckUserPasswordInDTO;
import com.example.userprofile.dao.dto.request.DeleteUserRoleInDTO;
import com.example.userprofile.dao.dto.request.GetUserInDTO;
import com.example.userprofile.dao.dto.request.ModifyUserInDTO;
import com.example.userprofile.dao.dto.request.ModifyUserPasswordInDTO;
import com.example.userprofile.dao.dto.response.CheckUserPasswordOutDTO;
import com.example.userprofile.dao.dto.response.DeleteUserOutDTO;
import com.example.userprofile.dao.dto.response.DeleteUserPasswordOutDTO;
import com.example.userprofile.dao.dto.response.DeleteUserRoleOutDTO;
import com.example.userprofile.dao.dto.response.ModifyUserOutDTO;
import com.example.userprofile.dao.dto.response.ModifyUserPasswordOutDTO;
import com.example.userprofile.dao.dto.response.UserOutDTO;
import com.example.userprofile.dto.request.DeleteUserDomainInDTO;
import com.example.userprofile.dto.request.ModifyUserDomainInDTO;
import com.example.userprofile.dto.request.ModifyUserPasswordDomainInDTO;
import com.example.userprofile.dto.request.ProfileDomainInDTO;
import com.example.userprofile.dto.response.DeleteUserDomainOutDTO;
import com.example.userprofile.dto.response.ModifyUserDomainOutDTO;
import com.example.userprofile.dto.response.ModifyUserPasswordDomainOutDTO;
import com.example.userprofile.dto.response.UserProfileDomainOutDTO;
import com.example.userprofile.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.userprofile.constant.Errors.INVALID_OLD_PASSWORD_ERROR_CODE;
import static com.example.userprofile.constant.Errors.INVALID_OLD_PASSWORD_ERROR_MESSAGE;

@Service
class UserProfileServiceImpl implements UserProfileService {

    private static final String USER_PASSWORD_MODIFIED_MESSAGE = "User password modified";
    private static final String USER_DELETED_MESSAGE = "User deleted";

    private final UsersDao usersDao;

    private final PasswordDao passwordDao;

    private final PermissionsDao permissionsDao;

    private final MapperComponent mapper;

    @Autowired
    public UserProfileServiceImpl(UsersDao usersDao, PasswordDao passwordDao, PermissionsDao permissionsDao, MapperComponent mapper) {
        this.usersDao = usersDao;
        this.passwordDao = passwordDao;
        this.permissionsDao = permissionsDao;
        this.mapper = mapper;
    }

    @Override
    public Mono<UserProfileDomainOutDTO> getProfile(final ProfileDomainInDTO getUserProfileRequest) {
        final Mono<UserOutDTO> userResponse = usersDao.getUser(mapper.map(getUserProfileRequest, GetUserInDTO.class));
        return userResponse.map(user -> mapper.map(user, UserProfileDomainOutDTO.class));
    }

    @Override
    public Mono<ModifyUserDomainOutDTO> modifyProfile(final ModifyUserDomainInDTO modifyProfileRequest) {
        final Mono<ModifyUserOutDTO> userResponse = usersDao.modifyUser(modifyProfileRequest.getId(), mapper.map(modifyProfileRequest, ModifyUserInDTO.class));
        return userResponse.map(user -> mapper.map(user, ModifyUserDomainOutDTO.class));
    }

    @Override
    public Mono<ModifyUserPasswordDomainOutDTO> modifyPassword(final ModifyUserPasswordDomainInDTO modifyPasswordRequest) {
        final Mono<CheckUserPasswordOutDTO> checkUserPasswordOutDTOMono = passwordDao.checkUserPassword(modifyPasswordRequest.getUserId(), new CheckUserPasswordInDTO(modifyPasswordRequest.getOldPassword()));
        return checkUserPasswordOutDTOMono.flatMap(success -> {
            final Mono<ModifyUserPasswordOutDTO> modifyUserPasswordResponse = passwordDao.modifyUserPassword(modifyPasswordRequest.getUserId(), mapper.map(modifyPasswordRequest, ModifyUserPasswordInDTO.class));
            return modifyUserPasswordResponse.map(res -> new ModifyUserPasswordDomainOutDTO(USER_PASSWORD_MODIFIED_MESSAGE));
        }).onErrorResume(error -> Mono.error(new BadRequestException(INVALID_OLD_PASSWORD_ERROR_CODE, INVALID_OLD_PASSWORD_ERROR_MESSAGE)));
    }

    @Override
    public Mono<DeleteUserDomainOutDTO> deleteProfile(final DeleteUserDomainInDTO deleteProfileRequest) {
        final Mono<DeleteUserOutDTO> deleteUserResponse = usersDao.deleteUser(deleteProfileRequest.getUserId());
        return deleteUserResponse.flatMap(res -> {
            final Mono<DeleteUserPasswordOutDTO> deleteUserPasswordResponse = passwordDao.deleteUserPassword(deleteProfileRequest.getUserId());
            return deleteUserPasswordResponse.flatMap(deletePasswordRes -> {
                final Mono<DeleteUserRoleOutDTO> deleteUserRoleResponse = permissionsDao.deleteUserRole(mapper.map(deleteProfileRequest, DeleteUserRoleInDTO.class));
                return deleteUserRoleResponse.map(deleteUserRoleRes -> new DeleteUserDomainOutDTO(USER_DELETED_MESSAGE));
            });
        });
    }

}
