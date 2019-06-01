package com.example.userprofile.service;

import com.example.userprofile.dto.request.DeleteUserDomainInDTO;
import com.example.userprofile.dto.request.ModifyUserDomainInDTO;
import com.example.userprofile.dto.request.ModifyUserPasswordDomainInDTO;
import com.example.userprofile.dto.request.ProfileDomainInDTO;
import com.example.userprofile.dto.response.DeleteUserDomainOutDTO;
import com.example.userprofile.dto.response.ModifyUserDomainOutDTO;
import com.example.userprofile.dto.response.ModifyUserPasswordDomainOutDTO;
import com.example.userprofile.dto.response.UserProfileDomainOutDTO;
import reactor.core.publisher.Mono;

public interface UserProfileService {

    Mono<UserProfileDomainOutDTO> getProfile(final ProfileDomainInDTO getUserProfileRequest);

    Mono<ModifyUserDomainOutDTO> modifyProfile(final ModifyUserDomainInDTO modifyProfileRequest);

    Mono<ModifyUserPasswordDomainOutDTO> modifyPassword(final ModifyUserPasswordDomainInDTO modifyPasswordRequest);

    Mono<DeleteUserDomainOutDTO> deleteProfile(final DeleteUserDomainInDTO deleteProfileRequest);

}
