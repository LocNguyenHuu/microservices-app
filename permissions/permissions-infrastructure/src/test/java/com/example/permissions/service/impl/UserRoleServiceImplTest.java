package com.example.permissions.service.impl;

import com.example.core.utils.http.errors.BadRequestException;
import com.example.core.utils.mapping.impl.MapperComponentImpl;
import com.example.permissions.dao.UserRoleDao;
import com.example.permissions.dto.request.CreateUserRoleDomainInDTO;
import com.example.permissions.dto.request.GetUserRoleDomainInDTO;
import com.example.permissions.dto.response.UserRoleDomainOutDTO;
import com.example.permissions.entity.UserRole;
import com.example.permissions.entity.model.UserRoleEnum;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRoleServiceImplTest {

    private static final String USER_ID = "1";
    private static final String USER_ROLE = "USER";
    private static final String INVALID_ROLE = "OWNER";

    @InjectMocks
    private UserRoleServiceImpl underTest;

    @Mock
    private UserRoleDao userRoleDao;

    @Before
    public void before() {
        underTest.setMapper(new MapperComponentImpl());
    }

    @Test
    public void shouldCreateRole() {
        // Given
        when(userRoleDao.save(any(UserRole.class))).thenReturn(buildUserRoleResponse());

        // When
        final Mono<UserRoleDomainOutDTO> response = underTest.createRole(buildCreateRoleRequest());

        // Then
        final UserRoleDomainOutDTO userRoleData = response.block();
        assertNotNull(userRoleData);
        assertEquals(USER_ID, userRoleData.getUserId());
        assertEquals(USER_ROLE, userRoleData.getRole());
    }

    @Test(expected = BadRequestException.class)
    public void shouldReturnAnErrorIfTheRoleIsIncorrect() {
        // When
        final Mono<UserRoleDomainOutDTO> response = underTest.createRole(buildInvalidCreateRoleRequest());

        // Then
        response.block();
    }

    @Test
    public void shouldReturnUserRole() {
        // Given
        when(userRoleDao.findOneByUserId(any())).thenReturn(buildUserRoleResponse());

        // When
        final Mono<UserRoleDomainOutDTO> response = underTest.getUserRole(buildGetUserRoleRequest());

        // Then
        final UserRoleDomainOutDTO userRoleData = response.block();
        assertNotNull(userRoleData);
        assertEquals(USER_ID, userRoleData.getUserId());
        assertEquals(USER_ROLE, userRoleData.getRole());
    }

    private CreateUserRoleDomainInDTO buildCreateRoleRequest() {
        CreateUserRoleDomainInDTO request = new CreateUserRoleDomainInDTO();
        request.setRole(USER_ROLE);
        request.setUserId(USER_ID);
        return request;
    }

    private CreateUserRoleDomainInDTO buildInvalidCreateRoleRequest() {
        CreateUserRoleDomainInDTO request = new CreateUserRoleDomainInDTO();
        request.setRole(INVALID_ROLE);
        request.setUserId(USER_ID);
        return request;
    }

    private Mono<UserRole> buildUserRoleResponse() {
        UserRole userRole = new UserRole(UserRoleEnum.USER);
        userRole.setUserId(USER_ID);
        return Mono.just(userRole);
    }

    private GetUserRoleDomainInDTO buildGetUserRoleRequest() {
        GetUserRoleDomainInDTO request = new GetUserRoleDomainInDTO();
        request.setUserId(USER_ID);
        return request;
    }

}
