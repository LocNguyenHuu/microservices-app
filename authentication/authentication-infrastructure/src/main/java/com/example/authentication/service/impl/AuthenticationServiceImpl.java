package com.example.authentication.service.impl;

import com.example.authentication.dao.PasswordManagerDao;
import com.example.authentication.dao.PermissionsDao;
import com.example.authentication.dao.UsersDao;
import com.example.authentication.dao.dto.request.CheckUserPasswordInDTO;
import com.example.authentication.dao.dto.request.FindUserByEmailInDTO;
import com.example.authentication.dao.dto.request.UserRoleInDTO;
import com.example.authentication.dao.dto.response.CheckUserPasswordOutDTO;
import com.example.authentication.dao.dto.response.FindUserByEmailOutDTO;
import com.example.authentication.dao.dto.response.UserRoleOutDTO;
import com.example.authentication.dto.request.LoginDomainInDTO;
import com.example.authentication.dto.response.LoginDomainOutDTO;
import com.example.authentication.service.AuthenticationService;
import com.example.core.security.JwtInfo;
import com.example.core.security.JwtUtil;
import com.example.core.utils.http.errors.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;

import static com.example.authentication.constant.Errors.INVALID_CREDENTIALS_ERROR_CODE;
import static com.example.authentication.constant.Errors.INVALID_CREDENTIALS_ERROR_MESSAGE;
import static com.example.core.security.SecurityConstants.JWT_PREFIX;
import static com.example.core.security.SecurityConstants.JWT_ROLE_CLAIM_KEY;
import static com.example.core.security.SecurityConstants.JWT_ROLE_PREFIX;

@Service
class AuthenticationServiceImpl implements AuthenticationService {

    private static final String WHITESPACE = " ";

    private final UsersDao usersDao;

    private final PasswordManagerDao passwordManagerDao;

    private final PermissionsDao permissionsDao;


    @Autowired
    public AuthenticationServiceImpl(UsersDao usersDao, PasswordManagerDao passwordManagerDao, PermissionsDao permissionsDao) {
        this.usersDao = usersDao;
        this.passwordManagerDao = passwordManagerDao;
        this.permissionsDao = permissionsDao;
    }

    public Mono<LoginDomainOutDTO> login(final LoginDomainInDTO loginRequest) {
        final FindUserByEmailInDTO loginRequestDTO = new FindUserByEmailInDTO(loginRequest.getEmail());
        final Mono<FindUserByEmailOutDTO> userDetailsResponse = usersDao.findUserByEmail(loginRequestDTO);
        return userDetailsResponse.flatMap(userDetails -> {
            final Mono<CheckUserPasswordOutDTO> userPasswordResponse = passwordManagerDao.checkUserPassword(userDetails.getId(), new CheckUserPasswordInDTO(loginRequest.getPassword()));
            return userPasswordResponse.flatMap(userPasswordDetails -> {
                final Mono<UserRoleOutDTO> userRoleResponse = permissionsDao.getUserRole(new UserRoleInDTO(userDetails.getId()));
                return userRoleResponse.map(userRole -> {
                    HashMap<String, Object> claims = new HashMap<>();
                    claims.put(JWT_ROLE_CLAIM_KEY, JWT_ROLE_PREFIX + userRole.getRole().name());
                    final String token = JwtUtil.generateToken(new JwtInfo(userDetails.getId(), claims));
                    return new LoginDomainOutDTO(JWT_PREFIX + WHITESPACE + token);
                });
            }).onErrorResume(error -> buildInvalidCredentialsException());
        }).onErrorResume(error -> buildInvalidCredentialsException());
    }

    private <T> Mono<T> buildInvalidCredentialsException() {
        return Mono.error(new BadRequestException(INVALID_CREDENTIALS_ERROR_CODE, INVALID_CREDENTIALS_ERROR_MESSAGE));
    }

}
