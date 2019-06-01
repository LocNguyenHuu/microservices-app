package com.example.passwordmanager.service.impl;

import com.example.core.utils.http.errors.BadRequestException;
import com.example.core.utils.mapping.MapperComponent;
import com.example.passwordmanager.dao.PasswordDao;
import com.example.passwordmanager.dto.request.CheckUserPasswordDomainInDTO;
import com.example.passwordmanager.dto.request.DeletePasswordDomainInDTO;
import com.example.passwordmanager.dto.request.ModifyPasswordDomainInDTO;
import com.example.passwordmanager.dto.request.NewPasswordDomainInDTO;
import com.example.passwordmanager.dto.response.CheckUserPasswordDomainOutDTO;
import com.example.passwordmanager.dto.response.DeletePasswordDomainOutDTO;
import com.example.passwordmanager.dto.response.ModifyPasswordDomainOutDTO;
import com.example.passwordmanager.dto.response.NewPasswordDomainOutDTO;
import com.example.passwordmanager.entity.Password;
import com.example.passwordmanager.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.passwordmanager.constant.Errors.INVALID_PASSWORD_ERROR_CODE;
import static com.example.passwordmanager.constant.Errors.INVALID_PASSWORD_ERROR_MESSAGE;

@Service
class PasswordServiceImpl implements PasswordService {

    private static final String PASSWORD_MODIFIED_MESSAGE = "Password modified";
    private static final String PASSWORD_DELETED_MESSAGE = "User password deleted";
    private static final String CORRECT_PASSWORD_MESSAGE = "Correct password";

    private final PasswordDao passwordDao;

    private final MapperComponent mapper;

    private final PasswordEncoder encoder;

    @Autowired
    public PasswordServiceImpl(PasswordDao passwordDao, MapperComponent mapper, PasswordEncoder encoder) {
        this.passwordDao = passwordDao;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    @Override
    public Mono<CheckUserPasswordDomainOutDTO> checkUserPassword(final CheckUserPasswordDomainInDTO checkUserPasswordRequest) {
        final Mono<Password> userPasswordResponse = passwordDao.findOneByUserId(checkUserPasswordRequest.getUserId());
        return userPasswordResponse.flatMap(userPassword -> {
            if (userPassword != null) {
                final boolean matches = encoder.matches(checkUserPasswordRequest.getPassword(), userPassword.getPassword());
                return matches
                        ? Mono.just(new CheckUserPasswordDomainOutDTO(CORRECT_PASSWORD_MESSAGE))
                        : buildInvalidPasswordException();
            }
            return buildInvalidPasswordException();
        });

    }

    @Override
    public Mono<NewPasswordDomainOutDTO> saveUserPassword(final NewPasswordDomainInDTO saveUserPasswordRequest) {
        Password password = mapper.map(saveUserPasswordRequest, Password.class);
        password.setPassword(encoder.encode(saveUserPasswordRequest.getPassword()));
        final Mono<Password> createdPassword = passwordDao.save(password);
        return createdPassword.map(p -> mapper.map(p, NewPasswordDomainOutDTO.class));
    }

    @Override
    public Mono<ModifyPasswordDomainOutDTO> modifyUserPassword(final ModifyPasswordDomainInDTO modifyUserPasswordRequest) {
        final Mono<Password> existingPasswordResponse = passwordDao.findOneByUserId(modifyUserPasswordRequest.getUserId());
        return existingPasswordResponse.flatMap(existingPassword -> {
            existingPassword.setPassword(encoder.encode(modifyUserPasswordRequest.getPassword()));
            final Mono<Password> updatePasswordResponse = passwordDao.save(existingPassword);
            return updatePasswordResponse.map(updatedPassword -> new ModifyPasswordDomainOutDTO(PASSWORD_MODIFIED_MESSAGE));
        });
    }

    @Override
    public Mono<DeletePasswordDomainOutDTO> deleteUserPassword(final DeletePasswordDomainInDTO deleteUserPasswordRequest) {
        final String userId = deleteUserPasswordRequest.getUserId();
        final Mono<Password> deletedPassword = passwordDao.findById(userId)
                .flatMap(oldValue ->
                        passwordDao.deleteById(userId)
                                .then(Mono.just(oldValue))
                ).single();
        return deletedPassword.map(res -> new DeletePasswordDomainOutDTO(PASSWORD_DELETED_MESSAGE));
    }

    private Mono<CheckUserPasswordDomainOutDTO> buildInvalidPasswordException() {
        return Mono.error(new BadRequestException(INVALID_PASSWORD_ERROR_CODE, INVALID_PASSWORD_ERROR_MESSAGE));
    }

}
