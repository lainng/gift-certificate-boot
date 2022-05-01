package com.piatnitsa.dao;

import com.piatnitsa.exception.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * This class is implementation of CRD<T> interface
 * and represents basic tools for work with database tables.
 * @param <T> type of entity.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public abstract class AbstractDao<T> implements CRDDao<T> {

    @PersistenceContext
    protected EntityManager entityManager;
    protected final Class<T> entityType;

    public AbstractDao(Class<T> entityType) {
        this.entityType = entityType;
    }

    @Override
    public Optional<T> getById(long id) {
        return Optional.of(entityManager.find(entityType, id));
    }

    @Override
    public List<T> getAll() {
        return entityManager.createQuery("select e from " + entityType.getSimpleName() + " e", entityType)
                .getResultList();
    }

    @Override
    public T insert(T item) throws DaoException {
        entityManager.persist(item);
        return item;
    }

    @Override
    public void removeById(long id) throws DaoException {
        T entity = entityManager.find(entityType, id);
        entityManager.remove(entity);
    }

}
