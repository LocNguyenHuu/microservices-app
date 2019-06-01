package com.example.users.service.impl;

import com.example.core.utils.http.errors.BadRequestException;
import com.example.core.utils.mapping.MapperComponent;
import com.example.users.dao.UserDao;
import com.example.users.dto.request.DeleteUserDomainInDTO;
import com.example.users.dto.request.GetUserByEmailDomainInDTO;
import com.example.users.dto.request.GetUserByIdDomainInDTO;
import com.example.users.dto.request.ModifyUserDomainInDTO;
import com.example.users.dto.request.NewUserDomainInDTO;
import com.example.users.dto.response.DeleteUserDomainOutDTO;
import com.example.users.dto.response.GetUserByEmailDomainOutDTO;
import com.example.users.dto.response.GetUserByIdDomainOutDTO;
import com.example.users.dto.response.GetUsersDomainOutDTO;
import com.example.users.dto.response.ModifyUserDomainOutDTO;
import com.example.users.dto.response.UserDomainOutDTO;
import com.example.users.entity.User;
import com.example.users.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.users.constant.Errors.USER_EMAIL_EXISTS_ERROR_CODE;
import static com.example.users.constant.Errors.USER_EMAIL_EXISTS_ERROR_MESSAGE;
import static com.example.users.constant.Errors.USER_NOT_EXISTS_ERROR_CODE;
import static com.example.users.constant.Errors.USER_NOT_EXISTS_ERROR_MESSAGE;

@Service
class UserServiceImpl implements UserService {

    private static final String USER_DELETED_MESSAGE = "User deleted";

    private final UserDao userDao;

    @Setter
    private MapperComponent mapper;

    @Autowired
    public UserServiceImpl(UserDao userDao, MapperComponent mapper) {
        this.userDao = userDao;
        this.mapper = mapper;
    }

    @Override
    public Mono<GetUserByIdDomainOutDTO> findById(final GetUserByIdDomainInDTO findUserRequest) {
        final Mono<User> userResponse = userDao.findOneByIdAndDeletedIsFalse(findUserRequest.getUserId());
        final Mono<Boolean> doesUserExistResponse = userResponse.hasElement();
        return doesUserExistResponse.flatMap(exists ->
                !exists
                        ? buildUserNotExistsError()
                        : userResponse.map(user -> mapper.map(user, GetUserByIdDomainOutDTO.class))
        );
    }

    @Override
    public Mono<UserDomainOutDTO> createUser(final NewUserDomainInDTO createUserRequest) {
        final Mono<Boolean> doesUserExistResponse = userDao.findOneByEmailAndDeletedIsFalse(createUserRequest.getEmail()).hasElement();
        return doesUserExistResponse.flatMap(exists ->
                exists ? Mono.error(new BadRequestException(USER_EMAIL_EXISTS_ERROR_CODE, USER_EMAIL_EXISTS_ERROR_MESSAGE)) : create(createUserRequest)
        );
    }

    @Override
    public Mono<ModifyUserDomainOutDTO> modifyUser(final ModifyUserDomainInDTO modifyUserRequest) {
        Mono<User> userResponse = userDao.findOneByIdAndDeletedIsFalse(modifyUserRequest.getId());
        final Mono<User> modificationResponse = userResponse.flatMap(u -> {
            if (!fieldIsEmpty(modifyUserRequest.getEmail())) {
                u.setEmail(modifyUserRequest.getEmail());
            }
            if (!fieldIsEmpty(modifyUserRequest.getName())) {
                u.setName(modifyUserRequest.getName());
            }
            if (!fieldIsEmpty(modifyUserRequest.getPhone())) {
                u.setPhone(modifyUserRequest.getPhone());
            }
            return userDao.save(u);
        });
        return modificationResponse.map(res -> mapper.map(res, ModifyUserDomainOutDTO.class));
    }

    @Override
    public Mono<DeleteUserDomainOutDTO> deleteUser(final DeleteUserDomainInDTO deleteUserRequest) {
        final String userId = deleteUserRequest.getUserId();
        Mono<User> deletedUser = userDao.findOneByIdAndDeletedIsFalse(userId)
                .flatMap(userData -> {
                            userData.setDeleted(true);
                            return userDao.save(userData);
                        }
                );
        return deletedUser.map(res -> new DeleteUserDomainOutDTO(USER_DELETED_MESSAGE));
    }

    @Override
    public Mono<GetUserByEmailDomainOutDTO> findByEmail(final GetUserByEmailDomainInDTO findByEmailRequest) {
        final Mono<User> userMono = userDao.findOneByEmailAndDeletedIsFalse(findByEmailRequest.getEmail());
        final Mono<Boolean> existsMono = userMono.hasElement();
        return existsMono.flatMap(exists ->
                !exists
                        ? buildUserNotExistsError()
                        : userMono.map(user -> new GetUserByEmailDomainOutDTO(user.getId(), user.getEmail()))
        );
    }

    @Override
    public Mono<GetUsersDomainOutDTO> getUsers() {
        Mono<List<User>> usersResponse = userDao.findAllByDeletedIsFalse().collectList();
        return usersResponse.map(users -> {
            final List<UserDomainOutDTO> userDomainOutDTOS = mapper.mapList(users, UserDomainOutDTO.class);
            return new GetUsersDomainOutDTO(userDomainOutDTOS);
        });
    }

    private boolean fieldIsEmpty(final String field) {
        return field == null || field.isEmpty();
    }

    private <T> Mono<T> buildUserNotExistsError() {
        return Mono.error(new BadRequestException(USER_NOT_EXISTS_ERROR_CODE, USER_NOT_EXISTS_ERROR_MESSAGE));
    }

    private Mono<UserDomainOutDTO> create(final NewUserDomainInDTO newUserDomainInDTO) {
        final Mono<User> createdUser = userDao.save(mapper.map(newUserDomainInDTO, User.class));
        return createdUser.map(m -> mapper.map(m, UserDomainOutDTO.class));
    }

}
