package com.example.core.security;

import org.junit.Test;

import java.util.HashMap;

import static com.example.core.security.SecurityConstants.JWT_PREFIX;
import static com.example.core.security.SecurityConstants.JWT_ROLE_CLAIM_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JwtUtilTest {

    private static final String USER_ID = "1";
    private static final String USER_ROLE = "USER";
    private static final String INVALID_TOKEN = "1234";
    private static final String WHITE_SPACE = " ";

    @Test
    public void shouldCreateToken() {
        // When
        final String token = JwtUtil.generateToken(buildJwtInfo());

        // Then
        assertTrue(JwtUtil.isValidToken(token));
        assertEquals(USER_ID, JwtUtil.getUserIdFromToken(token));
        assertEquals(USER_ROLE, JwtUtil.getClaimFromToken(token, JWT_ROLE_CLAIM_KEY));
    }

    @Test
    public void shouldValidateToken() {
        // When
        final Boolean isValidToken = JwtUtil.isValidToken(INVALID_TOKEN);
        assertFalse(isValidToken);
    }

    @Test
    public void shouldGetUserIdFromAuthHeader() {
        // Given
        final String token = JwtUtil.generateToken(buildJwtInfo());
        final String header = JWT_PREFIX + WHITE_SPACE + token;

        // When
        final String userIdFromToken = JwtUtil.getUserIdFromAuthHeader(header);

        // Then
        assertEquals(USER_ID, userIdFromToken);
    }

    private JwtInfo buildJwtInfo() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JWT_ROLE_CLAIM_KEY, USER_ROLE);
        return new JwtInfo(USER_ID, claims);
    }

}
