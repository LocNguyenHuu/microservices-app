package com.example.administration.controller;

import com.example.administration.dto.response.UsersDomainOutDTO;
import com.example.administration.dto.response.UsersPayload;
import com.example.administration.service.AdministrationService;
import com.example.core.utils.mapping.MapperComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AdministrationController {

    private final AdministrationService administrationService;

    private final MapperComponent mapper;

    @Autowired
    public AdministrationController(AdministrationService administrationService, MapperComponent mapper) {
        this.administrationService = administrationService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/users")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UsersPayload> getUsers() {
        final Mono<UsersDomainOutDTO> usersResponse = administrationService.getUsers();
        return usersResponse.map(users -> mapper.map(users, UsersPayload.class));
    }

}
