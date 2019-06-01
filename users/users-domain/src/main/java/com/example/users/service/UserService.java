package com.example.users.service;

import com.example.users.dto.request.DeleteUserDomainInDTO;
import com.example.users.dto.request.GetUserByEmailDomainInDTO;
import com.example.users.dto.request.GetUserByIdDomainInDTO;
import com.example.users.dto.request.ModifyUserDomainInDTO;
import com.example.users.dto.request.NewUserDomainInDTO;
import com.example.users.dto.response.DeleteUserDomainOutDTO;
import com.example.users.dto.response.GetUserByEmailDomainOutDTO;
import com.example.users.dto.response.GetUserByIdDomainOutDTO;
import com.example.users.dto.response.GetUsersDomainOutDTO;
import com.example.users.dto.response.ModifyUserDomainOutDTO;
import com.example.users.dto.response.UserDomainOutDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface UserService {

    Mono<GetUserByIdDomainOutDTO> findById(final GetUserByIdDomainInDTO findUserRequest);

    Mono<UserDomainOutDTO> createUser(final NewUserDomainInDTO createUserRequest);

    Mono<ModifyUserDomainOutDTO> modifyUser(final ModifyUserDomainInDTO modifyUserRequest);

    Mono<DeleteUserDomainOutDTO> deleteUser(final DeleteUserDomainInDTO deleteUserRequest);

    Mono<GetUserByEmailDomainOutDTO> findByEmail(final GetUserByEmailDomainInDTO findByEmailRequest);

    Mono<GetUsersDomainOutDTO> getUsers();

}
