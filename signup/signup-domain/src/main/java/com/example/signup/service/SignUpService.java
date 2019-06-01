package com.example.signup.service;

import com.example.signup.dto.request.SignUpDomainInDTO;
import com.example.signup.dto.response.SignUpDomainOutDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface SignUpService {

    Mono<SignUpDomainOutDTO> signUp(final SignUpDomainInDTO signUpRequest);

}
