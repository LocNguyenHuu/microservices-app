package com.example.passwordmanager.dao;

import com.example.passwordmanager.entity.Password;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PasswordDao extends ReactiveCrudRepository<Password, String> {

    Mono<Password> findOneByUserId(final String userId);

}
