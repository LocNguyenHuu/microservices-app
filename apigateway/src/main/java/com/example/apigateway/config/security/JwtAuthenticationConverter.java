package com.example.apigateway.config.security;

import com.example.apigateway.config.security.model.JwtPreAuthenticationToken;
import com.example.core.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static com.example.core.security.SecurityConstants.AUTHORIZATION_HEADER;
import static com.example.core.security.SecurityConstants.JWT_PREFIX;

@Component
public class JwtAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

    private static final String WHITE_SPACE = " ";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<Authentication> apply(ServerWebExchange exchange) throws BadCredentialsException {
        ServerHttpRequest request = exchange.getRequest();
        try {

            Authentication authentication = null;
            String authToken = null;
            String userId = null;

            final String bearerRequestHeader = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);

            if (bearerRequestHeader != null && bearerRequestHeader.startsWith(JWT_PREFIX + WHITE_SPACE)) {
                authToken = bearerRequestHeader.substring(7);
            }

            if (authToken != null) {
                try {
                    userId = JwtUtil.getUserIdFromToken(authToken);
                } catch (IllegalArgumentException e) {
                    logger.error("An error occurred while getting username from token", e);
                } catch (Exception e) {
                    logger.warn("Token expired", e);
                }
            } else {
                logger.warn("Bearer prefix not found");
            }

            logger.info("Checking authentication for user " + userId);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                return Mono.just(new JwtPreAuthenticationToken(authToken, bearerRequestHeader, userId));
            }

            return Mono.just(authentication);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid token");
        }
    }
}
