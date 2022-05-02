package com.piatnitsa.dao;

import com.piatnitsa.exception.DaoException;

/**
 * This interface describes CRUD operations for working with database tables.
 * @param <T> type of entity.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface CRUDDao<T> extends CRDDao<T> {

    /**
     * Updates an {@link T} entity at data source.
     * @param item an updated entity.
     * @return an updated entity.
     */
    T update(T item);
}
