package com.example.signup.controller;

import com.example.core.utils.mapping.MapperComponent;
import com.example.signup.dto.request.SignUpDomainInDTO;
import com.example.signup.dto.request.SignUpRequest;
import com.example.signup.dto.response.SignUpDomainOutDTO;
import com.example.signup.dto.response.SignUpPayload;
import com.example.signup.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class SignUpController {

    private final SignUpService signUpService;

    private final MapperComponent mapper;

    @Autowired
    public SignUpController(SignUpService signUpService, MapperComponent mapper) {
        this.signUpService = signUpService;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SignUpPayload> signUp(@RequestBody @Valid final SignUpRequest signUpRequest) {
        final Mono<SignUpDomainOutDTO> signUpResponse = signUpService.signUp(mapper.map(signUpRequest, SignUpDomainInDTO.class));
        return signUpResponse.map(signUpData -> mapper.map(signUpData, SignUpPayload.class));
    }

}
