package com.chern.dto.converter;

public interface Mapper<D,E> {

    D entityToDto(E entity);
    E dtoToEntity(D dto);
}
