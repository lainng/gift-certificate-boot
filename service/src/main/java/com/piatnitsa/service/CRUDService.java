package com.piatnitsa.service;

/**
 * This interface describes CRUD operations for working with objects.
 * @param <T> type of entity.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface CRUDService<T> extends CRDService<T> {

    /**
     * Updates an {@link T} entity at data source.
     * @param id an ID of specified entity.
     * @param entity an updated entity.
     */
    T update(long id, T entity);
}
