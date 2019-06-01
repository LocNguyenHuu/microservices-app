package com.example.userprofile.controller;

import com.example.core.security.JwtUtil;
import com.example.core.utils.mapping.MapperComponent;
import com.example.userprofile.dto.request.DeleteUserDomainInDTO;
import com.example.userprofile.dto.request.ModifyUserDomainInDTO;
import com.example.userprofile.dto.request.ModifyUserPasswordDomainInDTO;
import com.example.userprofile.dto.request.ModifyUserPasswordRequest;
import com.example.userprofile.dto.request.ModifyUserRequest;
import com.example.userprofile.dto.request.ProfileDomainInDTO;
import com.example.userprofile.dto.response.DeleteUserDomainOutDTO;
import com.example.userprofile.dto.response.DeleteUserPayload;
import com.example.userprofile.dto.response.ModifyUserDomainOutDTO;
import com.example.userprofile.dto.response.ModifyUserPasswordDomainOutDTO;
import com.example.userprofile.dto.response.ModifyUserPasswordPayload;
import com.example.userprofile.dto.response.ModifyUserPayload;
import com.example.userprofile.dto.response.UserProfileDomainOutDTO;
import com.example.userprofile.dto.response.UserProfilePayload;
import com.example.userprofile.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static com.example.core.security.SecurityConstants.AUTHORIZATION_HEADER;

@RestController
public class UserProfileController {

    private final UserProfileService userProfileService;

    private final MapperComponent mapper;


    @Autowired
    public UserProfileController(UserProfileService userProfileService, MapperComponent mapper) {
        this.userProfileService = userProfileService;
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserProfilePayload> getUserProfile(@RequestHeader(AUTHORIZATION_HEADER) final String authHeader) {
        final String userId = JwtUtil.getUserIdFromAuthHeader(authHeader);
        final Mono<UserProfileDomainOutDTO> profileResponse = userProfileService.getProfile(new ProfileDomainInDTO(userId));
        return profileResponse.map(profileData -> mapper.map(profileData, UserProfilePayload.class));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<ModifyUserPayload> modifyUserProfile(@RequestHeader(AUTHORIZATION_HEADER) final String authHeader, @RequestBody @Valid final ModifyUserRequest modifyUserRequest) {
        final String userId = JwtUtil.getUserIdFromAuthHeader(authHeader);
        ModifyUserDomainInDTO modifyUserDomainInDTO = mapper.map(modifyUserRequest, ModifyUserDomainInDTO.class);
        modifyUserDomainInDTO.setId(userId);
        final Mono<ModifyUserDomainOutDTO> modifyUserResponse = userProfileService.modifyProfile(modifyUserDomainInDTO);
        return modifyUserResponse.map(modifiedUserData -> mapper.map(modifiedUserData, ModifyUserPayload.class));
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ModifyUserPasswordPayload> modifyUserProfilePassword(@RequestHeader(AUTHORIZATION_HEADER) final String authHeader, @RequestBody @Valid final ModifyUserPasswordRequest modifyUserPasswordRequest) {
        final String userId = JwtUtil.getUserIdFromAuthHeader(authHeader);
        ModifyUserPasswordDomainInDTO newPasswordData = mapper.map(modifyUserPasswordRequest, ModifyUserPasswordDomainInDTO.class);
        newPasswordData.setUserId(userId);
        final Mono<ModifyUserPasswordDomainOutDTO> modifyPasswordResponse = userProfileService.modifyPassword(newPasswordData);
        return modifyPasswordResponse.map(res -> mapper.map(res, ModifyUserPasswordPayload.class));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<DeleteUserPayload> deleteUserProfile(@RequestHeader(AUTHORIZATION_HEADER) final String authHeader) {
        final String userId = JwtUtil.getUserIdFromAuthHeader(authHeader);
        final Mono<DeleteUserDomainOutDTO> deleteUserResponse = userProfileService.deleteProfile(new DeleteUserDomainInDTO(userId));
        return deleteUserResponse.map(res -> mapper.map(res, DeleteUserPayload.class));
    }

}
