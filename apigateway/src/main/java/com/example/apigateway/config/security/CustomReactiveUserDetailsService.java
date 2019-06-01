package com.example.apigateway.config.security;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

    private static final String EMPTY_PASSWORD = "";

    @Override
    public Mono<UserDetails> findByUsername(final String username) {
        return Mono.just(new User(username, EMPTY_PASSWORD, Collections.emptyList()));
    }
}
