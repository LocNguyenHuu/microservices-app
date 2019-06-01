package com.example.passwordmanager.service;

import com.example.passwordmanager.dto.request.CheckUserPasswordDomainInDTO;
import com.example.passwordmanager.dto.request.DeletePasswordDomainInDTO;
import com.example.passwordmanager.dto.request.ModifyPasswordDomainInDTO;
import com.example.passwordmanager.dto.request.NewPasswordDomainInDTO;
import com.example.passwordmanager.dto.response.CheckUserPasswordDomainOutDTO;
import com.example.passwordmanager.dto.response.DeletePasswordDomainOutDTO;
import com.example.passwordmanager.dto.response.ModifyPasswordDomainOutDTO;
import com.example.passwordmanager.dto.response.NewPasswordDomainOutDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface PasswordService {

    Mono<CheckUserPasswordDomainOutDTO> checkUserPassword(final CheckUserPasswordDomainInDTO checkUserPasswordRequest);

    Mono<NewPasswordDomainOutDTO> saveUserPassword(final NewPasswordDomainInDTO saveUserPasswordRequest);

    Mono<ModifyPasswordDomainOutDTO> modifyUserPassword(final ModifyPasswordDomainInDTO modifyUserPasswordRequest);

    Mono<DeletePasswordDomainOutDTO> deleteUserPassword(final DeletePasswordDomainInDTO deleteUserPasswordRequest);

}
