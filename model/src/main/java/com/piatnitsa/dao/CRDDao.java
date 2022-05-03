package com.piatnitsa.dao;

import java.util.List;
import java.util.Optional;

/**
 * This interface describes CRD operations for working with database tables.
 * @param <T> type of entity.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface CRDDao<T> {

    /**
     * Retrieves a {@link T} object by its ID.
     * @param id An ID of the object.
     * @return A {@link T} object.
     */
    Optional<T> getById(long id);

    /**
     * Retrieves a {@link List} of {@link T} objects.
     * @return A {@link List} of {@link T} objects.
     */
    List<T> getAll();

    /**
     * Method for saving an {@link T} entity.
     * @param item an {@link T} entity to save.
     * @return saved entity.
     */
    T insert(T item);

    /**
     * Removes an {@link T} entity from data source by its ID.
     * @param id an ID of {@link T} entity.
     */
    void removeById(long id);
}