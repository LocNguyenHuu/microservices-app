package com.example.userprofile.dao;

import com.example.userprofile.dao.dto.request.CheckUserPasswordInDTO;
import com.example.userprofile.dao.dto.request.ModifyUserPasswordInDTO;
import com.example.userprofile.dao.dto.response.CheckUserPasswordOutDTO;
import com.example.userprofile.dao.dto.response.DeleteUserPasswordOutDTO;
import com.example.userprofile.dao.dto.response.ModifyUserPasswordOutDTO;
import reactor.core.publisher.Mono;

public interface PasswordDao {

    Mono<ModifyUserPasswordOutDTO> modifyUserPassword(final String userId, final ModifyUserPasswordInDTO modifyUserPasswordRequest);

    Mono<DeleteUserPasswordOutDTO> deleteUserPassword(final String userId);

    Mono<CheckUserPasswordOutDTO> checkUserPassword(final String userId, final CheckUserPasswordInDTO checkUserPasswordRequest);

}
