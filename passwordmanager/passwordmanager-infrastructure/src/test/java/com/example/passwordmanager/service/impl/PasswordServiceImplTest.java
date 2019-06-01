package com.example.passwordmanager.service.impl;

import com.example.core.utils.http.errors.BadRequestException;
import com.example.passwordmanager.dao.PasswordDao;
import com.example.passwordmanager.dto.request.CheckUserPasswordDomainInDTO;
import com.example.passwordmanager.dto.response.CheckUserPasswordDomainOutDTO;
import com.example.passwordmanager.entity.Password;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordServiceImplTest {

    private static final String USER_ID = "1";
    private static final String PASSWORD = "1234";

    @InjectMocks
    private PasswordServiceImpl underTest;

    @Mock
    private PasswordDao passwordDao;

    @Mock
    private PasswordEncoder encoder;

    @Test
    public void shouldValidatePassword() {
        // Given
        when(passwordDao.findOneByUserId(anyString())).thenReturn(buildPasswordResponse());
        when(encoder.matches(anyString(), anyString())).thenReturn(true);

        // When
        final Mono<CheckUserPasswordDomainOutDTO> response = underTest.checkUserPassword(buildCheckUserPasswordDomainInDTO());

        // Then
        final CheckUserPasswordDomainOutDTO messageObject = response.block();
        assertNotNull(messageObject);
        assertNotNull(messageObject.getMessage());
    }

    @Test(expected = BadRequestException.class)
    public void shouldReturnErrorWhenPasswordIsIncorrect() {
        // Given
        when(passwordDao.findOneByUserId(anyString())).thenReturn(buildPasswordResponse());
        when(encoder.matches(anyString(), anyString())).thenReturn(false);

        // When
        Mono<CheckUserPasswordDomainOutDTO> response = underTest.checkUserPassword(buildCheckUserPasswordDomainInDTO());
        response.block();
    }

    private Mono<Password> buildPasswordResponse() {
        return Mono.just(new Password(USER_ID, PASSWORD));
    }

    private CheckUserPasswordDomainInDTO buildCheckUserPasswordDomainInDTO() {
        final CheckUserPasswordDomainInDTO request = new CheckUserPasswordDomainInDTO();
        request.setPassword(PASSWORD);
        request.setUserId(USER_ID);
        return request;
    }

}
