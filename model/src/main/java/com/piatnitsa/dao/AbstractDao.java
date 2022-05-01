package com.piatnitsa.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * This class is implementation of {@link CRDDao} interface
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
        return Optional.ofNullable(entityManager.find(entityType, id));
    }

    @Override
    public List<T> getAll() {
        return entityManager.createQuery("select e from " + entityType.getSimpleName() + " e", entityType)
                .getResultList();
    }

    @Override
    @Transactional
    public T insert(T item) {
        entityManager.persist(item);
        return item;
    }

    @Override
    @Transactional
    public void removeById(long id) {
        T entity = entityManager.find(entityType, id);
        entityManager.remove(entity);
    }

}
