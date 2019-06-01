package com.example.users.controller;

import com.example.core.utils.mapping.MapperComponent;
import com.example.users.dto.request.DeleteUserDomainInDTO;
import com.example.users.dto.request.GetUserByEmailDomainInDTO;
import com.example.users.dto.request.GetUserByIdDomainInDTO;
import com.example.users.dto.request.ModifyUserDomainInDTO;
import com.example.users.dto.request.ModifyUserRequest;
import com.example.users.dto.request.NewUserDomainInDTO;
import com.example.users.dto.request.NewUserRequest;
import com.example.users.dto.response.DeleteUserDomainOutDTO;
import com.example.users.dto.response.DeleteUserPayload;
import com.example.users.dto.response.GetUserByEmailDomainOutDTO;
import com.example.users.dto.response.GetUserByEmailPayload;
import com.example.users.dto.response.GetUserByIdDomainOutDTO;
import com.example.users.dto.response.GetUsersDomainOutDTO;
import com.example.users.dto.response.ModifyUserDomainOutDTO;
import com.example.users.dto.response.ModifyUserPayload;
import com.example.users.dto.response.NewUserPayload;
import com.example.users.dto.response.UserDomainOutDTO;
import com.example.users.dto.response.UserPayload;
import com.example.users.dto.response.UsersPayload;
import com.example.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    private final MapperComponent mapper;

    @Autowired
    public UserController(UserService userService, MapperComponent mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserPayload> getById(@PathVariable final String userId) {
        final Mono<GetUserByIdDomainOutDTO> userResponse = userService.findById(new GetUserByIdDomainInDTO(userId));
        return userResponse.map(userData -> mapper.map(userData, UserPayload.class));
    }

    @GetMapping(value = "/users/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<GetUserByEmailPayload> findByEmail(@PathVariable final String email) {
        final Mono<GetUserByEmailDomainOutDTO> userExistsResponse = userService.findByEmail(new GetUserByEmailDomainInDTO(email));
        return userExistsResponse.map(userExistsMessage -> mapper.map(userExistsMessage, GetUserByEmailPayload.class));
    }

    @PostMapping(value = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NewUserPayload> createUser(@RequestBody @Valid final NewUserRequest newUserRequestData) {
        final NewUserDomainInDTO newUserDataDTO = mapper.map(newUserRequestData, NewUserDomainInDTO.class);
        final Mono<UserDomainOutDTO> newUserDataResponse = userService.createUser(newUserDataDTO);
        return newUserDataResponse.map(newUserData -> mapper.map(newUserData, NewUserPayload.class));
    }

    @PatchMapping(value = "/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ModifyUserPayload> modifyUser(@PathVariable final String userId, @RequestBody @Valid final ModifyUserRequest modifyUserRequestData) {
        final ModifyUserDomainInDTO modifyUserRequestDTO = mapper.map(modifyUserRequestData, ModifyUserDomainInDTO.class);
        modifyUserRequestDTO.setId(userId);
        final Mono<ModifyUserDomainOutDTO> modifiedUserDataResponse = userService.modifyUser(modifyUserRequestDTO);
        return modifiedUserDataResponse.map(modifiedUserData -> mapper.map(modifiedUserData, ModifyUserPayload.class));
    }

    @DeleteMapping(value = "/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<DeleteUserPayload> deleteUser(@PathVariable final String userId) {
        final Mono<DeleteUserDomainOutDTO> deleteUserResponse = userService.deleteUser(new DeleteUserDomainInDTO(userId));
        return deleteUserResponse.map(res -> mapper.map(res, DeleteUserPayload.class));
    }

    @GetMapping(value = "/users")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UsersPayload> getUsers() {
        final Mono<GetUsersDomainOutDTO> usersResponse = userService.getUsers();
        return usersResponse.map(users -> mapper.map(users, UsersPayload.class));
    }

}
