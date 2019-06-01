package com.example.passwordmanager.controller;

import com.example.core.utils.mapping.MapperComponent;
import com.example.passwordmanager.dto.request.CheckPasswordRequest;
import com.example.passwordmanager.dto.request.CheckUserPasswordDomainInDTO;
import com.example.passwordmanager.dto.request.DeletePasswordDomainInDTO;
import com.example.passwordmanager.dto.request.ModifyPasswordDomainInDTO;
import com.example.passwordmanager.dto.request.ModifyPasswordRequest;
import com.example.passwordmanager.dto.request.NewPasswordDomainInDTO;
import com.example.passwordmanager.dto.request.NewPasswordRequest;
import com.example.passwordmanager.dto.response.CheckPasswordPayload;
import com.example.passwordmanager.dto.response.CheckUserPasswordDomainOutDTO;
import com.example.passwordmanager.dto.response.DeletePasswordDomainOutDTO;
import com.example.passwordmanager.dto.response.DeletePasswordPayload;
import com.example.passwordmanager.dto.response.ModifyPasswordDomainOutDTO;
import com.example.passwordmanager.dto.response.ModifyPasswordPayload;
import com.example.passwordmanager.dto.response.NewPasswordDomainOutDTO;
import com.example.passwordmanager.dto.response.NewPasswordPayload;
import com.example.passwordmanager.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class PasswordController {

    private PasswordService passwordService;

    private MapperComponent mapper;


    @Autowired
    public PasswordController(PasswordService passwordService, MapperComponent mapper) {
        this.passwordService = passwordService;
        this.mapper = mapper;
    }

    @PostMapping(value = "/passwords/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CheckPasswordPayload> checkPassword(@PathVariable final String userId, @RequestBody @Valid final CheckPasswordRequest checkPasswordRequest) {
        final Mono<CheckUserPasswordDomainOutDTO> checkUserPasswordResponse = passwordService.checkUserPassword(new CheckUserPasswordDomainInDTO(userId, checkPasswordRequest.getPassword()));
        return checkUserPasswordResponse.map(res -> mapper.map(res, CheckPasswordPayload.class));
    }

    @PostMapping(value = "/passwords")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NewPasswordPayload> savePassword(@RequestBody @Valid final NewPasswordRequest newPasswordRequest) {
        final NewPasswordDomainInDTO newPasswordRequestDTO = mapper.map(newPasswordRequest, NewPasswordDomainInDTO.class);
        final Mono<NewPasswordDomainOutDTO> newPasswordResponse = passwordService.saveUserPassword(newPasswordRequestDTO);
        return newPasswordResponse.map(res -> mapper.map(res, NewPasswordPayload.class));
    }

    @PutMapping(value = "/passwords/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ModifyPasswordPayload> modifyPassword(@PathVariable final String userId, @RequestBody @Valid final ModifyPasswordRequest modifyPasswordRequest) {
        final ModifyPasswordDomainInDTO modifyPasswordRequestDTO = mapper.map(modifyPasswordRequest, ModifyPasswordDomainInDTO.class);
        modifyPasswordRequestDTO.setUserId(userId);
        final Mono<ModifyPasswordDomainOutDTO> modifyPasswordResponse = passwordService.modifyUserPassword(modifyPasswordRequestDTO);
        return modifyPasswordResponse.map(res -> mapper.map(res, ModifyPasswordPayload.class));
    }

    @DeleteMapping(value = "/passwords/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<DeletePasswordPayload> deletePassword(@PathVariable final String userId) {
        final Mono<DeletePasswordDomainOutDTO> deletePasswordResponse = passwordService.deleteUserPassword(new DeletePasswordDomainInDTO(userId));
        return deletePasswordResponse.map(res -> mapper.map(res, DeletePasswordPayload.class));
    }

}
