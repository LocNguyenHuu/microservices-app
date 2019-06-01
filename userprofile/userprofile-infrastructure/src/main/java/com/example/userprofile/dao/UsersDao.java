package com.example.userprofile.dao;

import com.example.userprofile.dao.dto.request.GetUserInDTO;
import com.example.userprofile.dao.dto.request.ModifyUserInDTO;
import com.example.userprofile.dao.dto.response.DeleteUserOutDTO;
import com.example.userprofile.dao.dto.response.ModifyUserOutDTO;
import com.example.userprofile.dao.dto.response.UserOutDTO;
import reactor.core.publisher.Mono;

public interface UsersDao {

    Mono<UserOutDTO> getUser(final GetUserInDTO getUserRequest);

    Mono<ModifyUserOutDTO> modifyUser(final String userId, final ModifyUserInDTO modifyUserRequest);

    Mono<DeleteUserOutDTO> deleteUser(final String userId);

}
