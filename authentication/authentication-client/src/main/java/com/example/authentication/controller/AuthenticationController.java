package com.example.authentication.controller;

import com.example.authentication.dto.request.LoginDomainInDTO;
import com.example.authentication.dto.request.LoginRequest;
import com.example.authentication.dto.response.LoginDomainOutDTO;
import com.example.authentication.dto.response.LoginPayload;
import com.example.authentication.service.AuthenticationService;
import com.example.core.utils.mapping.MapperComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static com.example.core.security.SecurityConstants.AUTHORIZATION_HEADER;

@RestController
public class AuthenticationController {

    private static final String LOGIN_SUCCESSFUL_MESSAGE = "Login successful";

    private final AuthenticationService authenticationService;

    private final MapperComponent mapper;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, MapperComponent mapper) {
        this.authenticationService = authenticationService;
        this.mapper = mapper;
    }

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<LoginPayload>> login(@RequestBody @Valid final LoginRequest loginRequest) {
        final Mono<LoginDomainOutDTO> tokenResponse = authenticationService.login(mapper.map(loginRequest, LoginDomainInDTO.class));
        return tokenResponse.map(tokenInfo ->
                ResponseEntity.ok()
                        .header(AUTHORIZATION_HEADER, tokenInfo.getToken())
                        .body(new LoginPayload(LOGIN_SUCCESSFUL_MESSAGE))
        );

    }

}
