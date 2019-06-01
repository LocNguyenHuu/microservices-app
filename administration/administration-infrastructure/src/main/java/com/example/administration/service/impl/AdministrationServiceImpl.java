package com.example.administration.service.impl;

import com.example.administration.dao.UsersDao;
import com.example.administration.dao.dto.response.UsersOutDTO;
import com.example.administration.dto.response.UsersDomainOutDTO;
import com.example.administration.service.AdministrationService;
import com.example.core.utils.mapping.MapperComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
class AdministrationServiceImpl implements AdministrationService {

    private final UsersDao usersDao;

    private final MapperComponent mapper;

    @Autowired
    public AdministrationServiceImpl(UsersDao usersDao, MapperComponent mapper) {
        this.usersDao = usersDao;
        this.mapper = mapper;
    }


    @Override
    public Mono<UsersDomainOutDTO> getUsers() {
        final Mono<UsersOutDTO> usersResponse = usersDao.getUsers();
        return usersResponse.map(users -> mapper.map(users, UsersDomainOutDTO.class));
    }
}
