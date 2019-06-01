package com.example.apigateway.config.security;

import com.example.apigateway.config.security.model.JwtAuthenticationToken;
import com.example.apigateway.config.security.model.JwtPreAuthenticationToken;
import com.example.core.security.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;

import static com.example.core.security.SecurityConstants.JWT_ROLE_CLAIM_KEY;

@Component
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final ReactiveUserDetailsService userDetailsService;

    public CustomReactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Authentication> authenticate(final Authentication authentication) {
        if (authentication instanceof JwtPreAuthenticationToken) {
            return Mono.just(authentication)
                    .switchIfEmpty(Mono.defer(this::raiseBadCredentials))
                    .cast(JwtPreAuthenticationToken.class)
                    .flatMap(this::authenticateToken)
                    .publishOn(Schedulers.parallel())
                    .onErrorResume(e -> raiseBadCredentials())
                    .map(u -> new JwtAuthenticationToken(u.getUsername(), u.getAuthorities()));
        }

        return Mono.just(authentication);
    }

    private <T> Mono<T> raiseBadCredentials() {
        return Mono.error(new BadCredentialsException("Invalid Credentials"));
    }

    private Mono<UserDetails> authenticateToken(final JwtPreAuthenticationToken jwtPreAuthenticationToken) {
        try {
            final String authToken = jwtPreAuthenticationToken.getAuthToken();
            final String username = jwtPreAuthenticationToken.getUsername();

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (JwtUtil.isValidToken(authToken)) {
                    final Mono<UserDetails> userDetailsResponse = this.userDetailsService.findByUsername(username);
                    return userDetailsResponse.map(userDetails -> {
                        final String roleFromToken = JwtUtil.getClaimFromToken(authToken, JWT_ROLE_CLAIM_KEY);
                        final List<GrantedAuthority> grantedAuthorityList = Collections.singletonList(new SimpleGrantedAuthority(roleFromToken));
                        return new User(userDetails.getUsername(), userDetails.getPassword(), grantedAuthorityList);
                    });
                }
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid token");
        }

        return null;
    }

}