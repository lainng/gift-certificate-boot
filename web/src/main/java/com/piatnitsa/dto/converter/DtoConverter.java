package com.piatnitsa.dto.converter;

public interface DtoConverter<D, E> {

    E toEntity(D dto);
    D toDto(E entity);
}
