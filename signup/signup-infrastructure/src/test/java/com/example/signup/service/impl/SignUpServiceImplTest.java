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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SignUpServiceImplTest {

    private static final String USER_ID = "1";
    private static final String EMAIL = "test@email.com";
    private static final String NAME = "Sergio";
    private static final String PHONE = "123456";
    private static final String PASSWORD = "1234";

    @InjectMocks
    private SignUpServiceImpl underTest;

    @Mock
    private UsersDao usersDao;

    @Mock
    private PasswordManagerDao passwordManagerDao;

    @Mock
    private PermissionsDao permissionsDao;

    @Test
    public void shouldCreateAnUser() {
        // Given
        when(usersDao.createUser(any(NewUserInDTO.class))).thenReturn(buildNewUserOutDTO());
        when(passwordManagerDao.createPassword(any(NewUserPasswordInDTO.class))).thenReturn(buildNewUserPasswordOutDTO());
        when(permissionsDao.createUserRole(any(NewUserRoleInDTO.class))).thenReturn(buildNewUserRoleOutDTO());

        // When
        final Mono<SignUpDomainOutDTO> response = underTest.signUp(buildRequest());

        // Then
        final SignUpDomainOutDTO responseObject = response.block();
        assertNotNull(responseObject);
        assertEquals(USER_ID, responseObject.getId());
        assertEquals(EMAIL, responseObject.getEmail());
        assertEquals(NAME, responseObject.getName());
        assertEquals(PHONE, responseObject.getPhone());
    }

    private Mono<NewUserOutDTO> buildNewUserOutDTO() {
        NewUserOutDTO createUserResponse = new NewUserOutDTO();
        createUserResponse.setId(USER_ID);
        createUserResponse.setEmail(EMAIL);
        createUserResponse.setName(NAME);
        createUserResponse.setPhone(PHONE);
        return Mono.just(createUserResponse);
    }

    private Mono<NewUserPasswordOutDTO> buildNewUserPasswordOutDTO() {
        NewUserPasswordOutDTO createPasswordResponse = new NewUserPasswordOutDTO();
        createPasswordResponse.setUserId(USER_ID);
        createPasswordResponse.setPassword(EMAIL);
        return Mono.just(createPasswordResponse);
    }

    private Mono<NewUserRoleOutDTO> buildNewUserRoleOutDTO() {
        NewUserRoleOutDTO createUserRoleResponse = new NewUserRoleOutDTO();
        createUserRoleResponse.setUserId(USER_ID);
        createUserRoleResponse.setRole(UserRoleEnum.USER);
        return Mono.just(createUserRoleResponse);
    }

    private SignUpDomainInDTO buildRequest() {
        final SignUpDomainInDTO request = new SignUpDomainInDTO();
        request.setEmail(EMAIL);
        request.setName(NAME);
        request.setPhone(PHONE);
        request.setPassword(PASSWORD);
        return request;
    }

}
