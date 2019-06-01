package com.example.users.service.impl;

import com.example.core.utils.http.errors.BadRequestException;
import com.example.core.utils.mapping.impl.MapperComponentImpl;
import com.example.users.dao.UserDao;
import com.example.users.dto.request.GetUserByEmailDomainInDTO;
import com.example.users.dto.request.GetUserByIdDomainInDTO;
import com.example.users.dto.request.NewUserDomainInDTO;
import com.example.users.dto.response.GetUserByEmailDomainOutDTO;
import com.example.users.dto.response.GetUserByIdDomainOutDTO;
import com.example.users.dto.response.UserDomainOutDTO;
import com.example.users.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String USER_ID = "1";
    private static final String EMAIL = "test@gmail.co";
    private static final String NAME = "NAME";
    private static final String PHONE = "123456";

    @InjectMocks
    private UserServiceImpl underTest;

    @Mock
    private UserDao userDao;

    @Before
    public void before() {
        underTest.setMapper(new MapperComponentImpl());
    }

    @Test
    public void shouldReturnAnUserById() {
        // Given
        when(userDao.findOneByIdAndDeletedIsFalse(anyString())).thenReturn(buildUserResponse());

        // When
        final Mono<GetUserByIdDomainOutDTO> response = underTest.findById(buildGetUserByIdRequest());

        // Then
        final GetUserByIdDomainOutDTO userResponse = response.block();
        assertNotNull(userResponse);
        assertEquals(USER_ID, userResponse.getId());
        assertEquals(EMAIL, userResponse.getEmail());
        assertEquals(NAME, userResponse.getName());
        assertEquals(PHONE, userResponse.getPhone());
    }

    @Test(expected = BadRequestException.class)
    public void shouldReturnAnErrorWhileGettingAnUserByIdThatDoesNotExists() {
        // Given
        when(userDao.findOneByIdAndDeletedIsFalse(anyString())).thenReturn(Mono.empty());

        // When
        final Mono<GetUserByIdDomainOutDTO> response = underTest.findById(buildGetUserByIdRequest());

        // Then
        response.block();
    }

    @Test
    public void shouldCreateAnUser() {
        // Given
        when(userDao.findOneByEmailAndDeletedIsFalse(anyString())).thenReturn(Mono.empty());
        when(userDao.save(any(User.class))).thenReturn(buildUserResponse());

        // When
        final Mono<UserDomainOutDTO> response = underTest.createUser(buildCreateUserRequest());

        // Then
        final UserDomainOutDTO createUserResponse = response.block();
        assertNotNull(createUserResponse);
        assertEquals(USER_ID, createUserResponse.getId());
        assertEquals(EMAIL, createUserResponse.getEmail());
        assertEquals(NAME, createUserResponse.getName());
        assertEquals(PHONE, createUserResponse.getPhone());
    }

    @Test(expected = BadRequestException.class)
    public void shouldReturnErrorIfNewUserEmailAlreadyExists() {
        // Given
        when(userDao.findOneByEmailAndDeletedIsFalse(anyString())).thenReturn(buildUserResponse());

        // When
        final Mono<UserDomainOutDTO> response = underTest.createUser(buildCreateUserRequest());

        // Then
        response.block();
    }

    @Test
    public void shouldReturnAnUserByEmail() {
        // Given
        when(userDao.findOneByEmailAndDeletedIsFalse(anyString())).thenReturn(buildUserResponse());

        // When
        final Mono<GetUserByEmailDomainOutDTO> response = underTest.findByEmail(buildGetUserByEmailRequest());

        // Then
        final GetUserByEmailDomainOutDTO userResponse = response.block();
        assertNotNull(userResponse);
        assertEquals(USER_ID, userResponse.getId());
        assertEquals(EMAIL, userResponse.getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void shouldReturnAnErrorWhileGettingAnUserByEmailThatDoesNotExists() {
        // Given
        when(userDao.findOneByEmailAndDeletedIsFalse(anyString())).thenReturn(Mono.empty());

        // When
        final Mono<GetUserByEmailDomainOutDTO> response = underTest.findByEmail(buildGetUserByEmailRequest());

        // Then
        response.block();
    }


    private GetUserByIdDomainInDTO buildGetUserByIdRequest() {
        GetUserByIdDomainInDTO request = new GetUserByIdDomainInDTO();
        request.setUserId(USER_ID);
        return request;
    }

    private Mono<User> buildUserResponse() {
        User user = new User(EMAIL, NAME, PHONE);
        user.setId(USER_ID);
        return Mono.just(user);
    }

    private NewUserDomainInDTO buildCreateUserRequest() {
        NewUserDomainInDTO request = new NewUserDomainInDTO();
        request.setEmail(USER_ID);
        request.setName(NAME);
        request.setPhone(PHONE);
        return request;
    }

    private GetUserByEmailDomainInDTO buildGetUserByEmailRequest() {
        GetUserByEmailDomainInDTO request = new GetUserByEmailDomainInDTO();
        request.setEmail(EMAIL);
        return request;
    }

}
