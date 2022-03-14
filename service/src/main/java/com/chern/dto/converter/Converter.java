package com.chern.dto.converter;

public interface Converter<D,E> {

    D entityToDtoConverter(E entity);
    E dtoToEntityConverter(D entity);
}
