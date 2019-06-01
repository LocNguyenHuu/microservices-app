package com.example.core.utils.mapping.impl;

import com.example.core.utils.mapping.MapperComponent;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapperComponentImpl implements MapperComponent {

    private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Override
    public <E, T> T map(E origin, Class<T> targetClass) {
        return mapperFactory.getMapperFacade().map(origin, targetClass);
    }

    @Override
    public <E, T> List<T> mapList(List<E> origin, Class<T> targetClass) {
        return mapperFactory.getMapperFacade().mapAsList(origin, targetClass);
    }
}
