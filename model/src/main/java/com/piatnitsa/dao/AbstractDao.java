package com.piatnitsa.dao;

import com.piatnitsa.dao.creator.QueryCreator;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    protected final QueryCreator<T> queryCreator;
    protected final Class<T> entityType;

    public AbstractDao(QueryCreator<T> queryCreator, Class<T> entityType) {
        this.queryCreator = queryCreator;
        this.entityType = entityType;
    }

    @Override
    public Optional<T> findById(long id) {
        return Optional.ofNullable(entityManager.find(entityType, id));
    }

    @Override
    public List<T> findAll(Pageable pageable) {
        return entityManager.createQuery("select e from " + entityType.getSimpleName() + " e", entityType)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    @Transactional
    public T insert(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void removeById(long id) {
        T entity = entityManager.find(entityType, id);
        entityManager.remove(entity);
    }

    @Override
    public List<T> findWithFilter(MultiValueMap<String, String> params, Pageable pageable) {
        CriteriaQuery<T> criteriaQuery = queryCreator.createFilteringGetQuery(
                params,
                entityManager.getCriteriaBuilder()
        );
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultStream()
                .distinct()
                .collect(Collectors.toList());
    }

}
