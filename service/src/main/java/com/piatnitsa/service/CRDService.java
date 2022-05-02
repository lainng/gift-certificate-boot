package com.piatnitsa.service;

import com.piatnitsa.exception.IncorrectParameterException;

import java.util.List;

/**
 * This interface describes CRD operations for working with objects.
 * @param <T> type of entity.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface CRDService<T> {

    /**
     * Retrieves a {@link T} object by its ID.
     * @param id An ID of the object.
     * @return A {@link T} object.
     * @throws IncorrectParameterException if the ID is not valid.
     */
    T getById(long id) throws IncorrectParameterException;

    /**
     * Retrieves a {@link List} of {@link T} objects.
     * @return A {@link List} of {@link T} objects.
     */
    List<T> getAll();

    /**
     * Method for saving an {@link T} entity
     * @param item an {@link T} entity to save.
     * @throws IncorrectParameterException if the {@link T} entity contains not valid data.
     */
    T insert(T item) throws IncorrectParameterException;

    /**
     * Removes an {@link T} entity from data source by its ID.
     * @param id an ID of {@link T} entity.
     * @throws IncorrectParameterException if the ID is not valid.
     */
    void removeById(long id) throws IncorrectParameterException;
}
