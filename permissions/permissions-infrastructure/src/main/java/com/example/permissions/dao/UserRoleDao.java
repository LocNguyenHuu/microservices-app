package com.example.permissions.dao;

import com.example.permissions.entity.UserRole;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRoleDao extends ReactiveCrudRepository<UserRole, String> {

    Mono<UserRole> findOneByUserId(final String userId);

}
