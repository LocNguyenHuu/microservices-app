package com.example.administration.service;

import com.example.administration.dto.response.UsersDomainOutDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface AdministrationService {

    Mono<UsersDomainOutDTO> getUsers();

}
