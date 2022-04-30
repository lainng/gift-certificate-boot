package com.piatnitsa.dao;

import com.piatnitsa.exception.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 * This class is implementation of CRD<T> interface
 * and represents basic tools for work with database tables.
 * @param <T> type of entity.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public abstract class AbstractDao<T> implements CRDDao<T>{
    @PersistenceContext
    protected EntityManager entityManager;
    protected final Class<T> entityType;

    public AbstractDao(Class<T> entityType) {
        this.entityType = entityType;
    }


    @Override
    public void removeById(long id) throws DaoException {
        T entity = entityManager.find(entityType, id);
        entityManager.remove(entity);
    }

    @Override
    public Optional<T> getById(long id) {
        return Optional.of(entityManager.find(entityType, id));
    }

}
