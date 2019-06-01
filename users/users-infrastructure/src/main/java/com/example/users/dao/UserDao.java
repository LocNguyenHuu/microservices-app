package com.example.users.dao;

import com.example.users.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserDao extends ReactiveCrudRepository<User, String> {

    Mono<User> findOneByEmailAndDeletedIsFalse(String email);

    Flux<User> findAllByDeletedIsFalse();

    Mono<User> findOneByIdAndDeletedIsFalse(final String userId);

}
