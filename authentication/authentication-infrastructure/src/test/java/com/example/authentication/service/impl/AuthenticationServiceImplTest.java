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
import com.example.authentication.dao.dto.response.model.UserRoleEnum;
import com.example.authentication.dto.request.LoginDomainInDTO;
import com.example.authentication.dto.response.LoginDomainOutDTO;
import com.example.core.security.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceImplTest {

    private static final String EMAIL = "test@test.com";
    private static final String USER_ID = "1";
    private static final String PASSWORD = "1234";
    private static final String WHITE_SPACE = " ";

    @InjectMocks
    private AuthenticationServiceImpl underTest;

    @Mock
    private PermissionsDao permissionsDao;

    @Mock
    private PasswordManagerDao passwordManagerDao;

    @Mock
    private UsersDao usersDao;

    @Test
    public void shouldGenerateToken() {
        // Given
        when(permissionsDao.getUserRole(any(UserRoleInDTO.class))).thenReturn(Mono.just(buildUserRoleOutDTO()));
        when(passwordManagerDao.checkUserPassword(anyString(), any(CheckUserPasswordInDTO.class))).thenReturn(Mono.just(new CheckUserPasswordOutDTO()));
        FindUserByEmailOutDTO findUserByEmailOutDTO = new FindUserByEmailOutDTO();
        findUserByEmailOutDTO.setEmail(EMAIL);
        findUserByEmailOutDTO.setId(USER_ID);
        when(usersDao.findUserByEmail(any(FindUserByEmailInDTO.class))).thenReturn(Mono.just(findUserByEmailOutDTO));

        // When
        final Mono<LoginDomainOutDTO> response = underTest.login(buildLoginDomainInDTO());

        // Then
        final LoginDomainOutDTO tokenObject = response.block();
        assertNotNull(tokenObject);
        final String token = tokenObject.getToken().split(WHITE_SPACE)[1];
        assertTrue(JwtUtil.isValidToken(token));
        assertEquals(USER_ID, JwtUtil.getUserIdFromToken(token));
    }

    private UserRoleOutDTO buildUserRoleOutDTO() {
        final UserRoleOutDTO userRoleOutDTO = new UserRoleOutDTO();
        userRoleOutDTO.setRole(UserRoleEnum.ADMIN);
        userRoleOutDTO.setUserId(USER_ID);
        return userRoleOutDTO;
    }

    private LoginDomainInDTO buildLoginDomainInDTO() {
        LoginDomainInDTO loginDomainInDTO = new LoginDomainInDTO();
        loginDomainInDTO.setEmail(EMAIL);
        loginDomainInDTO.setPassword(PASSWORD);
        return loginDomainInDTO;
    }

}
