package com.piatnitsa.dto.converter;

/**
 * This interface provides tools converting {@link E} entities to {@link D} DTOs.
 * @param <D> DTO type.
 * @param <E> entity type.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface DtoConverter<D, E> {

    /**
     * Converts the {@link D} DTO to the {@link E} entity.
     * @param dto {@link D} DTO.
     * @return converted {@link E} entity.
     */
    E toEntity(D dto);

    /**
     * Converts the {@link E} entity to the {@link D} DTO.
     * @param entity {@link E} entity.
     * @return converted {@link D} DTO.
     */
    D toDto(E entity);
}
