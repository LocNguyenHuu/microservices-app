package com.example.administration.mapper;

import com.example.administration.dto.response.UsersDomainOutDTO;
import com.example.administration.dto.response.UsersPayload;
import com.example.administration.dto.response.model.UserSubPayload;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsersDomainOutDTOToUsersPayloadConverter extends CustomConverter<UsersDomainOutDTO, UsersPayload> {

    @Override
    public UsersPayload convert(UsersDomainOutDTO source, Type<? extends UsersPayload> destinationType, MappingContext mappingContext) {
        final List<UserSubPayload> userList = mapperFacade.mapAsList(source.getUsers(), UserSubPayload.class);
        return new UsersPayload(userList);
    }
}
