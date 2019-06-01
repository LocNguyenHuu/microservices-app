package com.example.signup.service.impl;

import com.example.signup.dao.PasswordManagerDao;
import com.example.signup.dao.PermissionsDao;
import com.example.signup.dao.UsersDao;
import com.example.signup.dao.dto.model.UserRoleEnum;
import com.example.signup.dao.dto.request.NewUserInDTO;
import com.example.signup.dao.dto.request.NewUserPasswordInDTO;
import com.example.signup.dao.dto.request.NewUserRoleInDTO;
import com.example.signup.dao.dto.response.NewUserOutDTO;
import com.example.signup.dao.dto.response.NewUserPasswordOutDTO;
import com.example.signup.dao.dto.response.NewUserRoleOutDTO;
import com.example.signup.dto.request.SignUpDomainInDTO;
import com.example.signup.dto.response.SignUpDomainOutDTO;
import com.example.signup.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
class SignUpServiceImpl implements SignUpService {

    private final UsersDao usersDao;

    private final PasswordManagerDao passwordManagerDao;

    private final PermissionsDao permissionsDao;

    @Autowired
    public SignUpServiceImpl(UsersDao usersDao, PasswordManagerDao passwordManagerDao, PermissionsDao permissionsDao) {
        this.usersDao = usersDao;
        this.passwordManagerDao = passwordManagerDao;
        this.permissionsDao = permissionsDao;
    }

    @Override
    public Mono<SignUpDomainOutDTO> signUp(final SignUpDomainInDTO signUpRequest) {
        final NewUserInDTO newUserDTO = new NewUserInDTO(signUpRequest.getEmail(), signUpRequest.getName(), signUpRequest.getPhone());
        final Mono<NewUserOutDTO> newUserResponse = usersDao.createUser(newUserDTO);
        return newUserResponse.flatMap(userData -> {
            final NewUserPasswordInDTO newUserPasswordRequest = new NewUserPasswordInDTO(userData.getId(), signUpRequest.getPassword());
            final Mono<NewUserPasswordOutDTO> newPasswordResponse = passwordManagerDao.createPassword(newUserPasswordRequest);
            return newPasswordResponse.flatMap(result -> {
                final Mono<NewUserRoleOutDTO> createUserRoleResponse = permissionsDao.createUserRole(new NewUserRoleInDTO(userData.getId(), UserRoleEnum.USER));
                return createUserRoleResponse.map(userRole -> new SignUpDomainOutDTO(userData.getId(), userData.getEmail(), userData.getName(), userData.getPhone()));
            });
        });
    }
}