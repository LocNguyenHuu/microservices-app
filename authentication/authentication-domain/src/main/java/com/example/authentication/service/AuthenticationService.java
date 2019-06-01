package com.example.authentication.service;

import com.example.authentication.dto.request.LoginDomainInDTO;
import com.example.authentication.dto.response.LoginDomainOutDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface AuthenticationService {

    Mono<LoginDomainOutDTO> login(final LoginDomainInDTO loginRequest);

}
