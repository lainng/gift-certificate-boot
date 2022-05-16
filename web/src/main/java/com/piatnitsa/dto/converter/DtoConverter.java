package com.piatnitsa.dto.converter;

public interface DtoConverter<D, E> {

    E toEntity(D object);
    D toDto(E entity);
}
