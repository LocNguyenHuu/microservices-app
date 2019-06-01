package com.example.core.utils.mapping;

import java.util.List;

public interface MapperComponent {

    <E, T> T map(E origin, Class<T> targetClass);

    <E, T> List<T> mapList(List<E> origin, Class<T> targetClass);

}