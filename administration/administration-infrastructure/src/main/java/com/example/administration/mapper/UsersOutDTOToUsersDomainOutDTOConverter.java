package com.example.administration.mapper;

import com.example.administration.dao.dto.response.UsersOutDTO;
import com.example.administration.dto.response.UsersDomainOutDTO;
import com.example.administration.dto.response.model.UserSubDomainOutDTO;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsersOutDTOToUsersDomainOutDTOConverter extends CustomConverter<UsersOutDTO, UsersDomainOutDTO> {

    @Override
    public UsersDomainOutDTO convert(UsersOutDTO source, Type<? extends UsersDomainOutDTO> destinationType, MappingContext mappingContext) {
        final List<UserSubDomainOutDTO> userList = mapperFacade.mapAsList(source.getUsers(), UserSubDomainOutDTO.class);
        return new UsersDomainOutDTO(userList);
    }
}
