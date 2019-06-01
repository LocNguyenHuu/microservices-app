package com.example.core.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

public class JwtUtil implements Serializable {

    private static final Long serialVersionUID = 1L;

    private static final String AUDIENCE_UNKNOWN = "unknown";

    private static final String SECRET = "m6sXDUKZSY154e9N";

    private static final Long EXPIRATION = 86400000L;

    public static String getUserIdFromAuthHeader(final String authHeader) {
        final String token = authHeader.split(" ")[1];
        return getClaimFromToken(token, Claims::getSubject);
    }

    public static String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public static Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public static String getClaimFromToken(final String token, final String key) {
        final Claims allClaimsFromToken = getAllClaimsFromToken(token);
        return allClaimsFromToken.get(key, String.class);
    }

    public static String generateToken(final JwtInfo jwtInfo) {
        final Date createdDate = DefaultClock.INSTANCE.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(jwtInfo.getClaims())
                .setSubject(jwtInfo.getUserId())
                .setAudience(generateAudience())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

    }

    public static Boolean isValidToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (MalformedJwtException e) {
            return false;
        }
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(DefaultClock.INSTANCE.now());
    }

    private static String generateAudience() {
        return AUDIENCE_UNKNOWN;
    }

    private static Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + EXPIRATION * 1000);
    }

    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

}
