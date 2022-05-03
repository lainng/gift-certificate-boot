package com.piatnitsa.service;

import com.piatnitsa.dao.CRDDao;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.validator.IdentifiableValidator;

import java.util.List;
import java.util.Optional;

/**
 * This class is implementation of interface {@link CRDService} and is designed for basic work with objects.
 * @param <T> type of entity.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public abstract class AbstractService<T> implements CRDService<T> {
    private final CRDDao<T> dao;

    protected AbstractService(CRDDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public T getById(long id) throws IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        Optional<T> entity = dao.getById(id);
        if (!entity.isPresent()) {
            throw new IncorrectParameterException();
        }
        return entity.get();
    }

    @Override
    public List<T> getAll() {
        return dao.getAll();
    }

    @Override
    public void removeById(long id) throws IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        dao.removeById(id);
    }
}