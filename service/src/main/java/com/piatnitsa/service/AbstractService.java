package com.piatnitsa.service;

import com.piatnitsa.dao.CRDDao;
import com.piatnitsa.exception.ExceptionMessageHolder;
import com.piatnitsa.exception.ExceptionMessageKey;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.exception.NoSuchEntityException;
import com.piatnitsa.validator.FilterParameterValidator;
import com.piatnitsa.validator.IdentifiableValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.MultiValueMap;

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
    public T getById(long id) {
        validateId(id);
        Optional<T> entity = dao.findById(id);
        if (!entity.isPresent()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }
        return entity.get();
    }

    @Override
    public List<T> getAll(int page, int size) {
        ExceptionMessageHolder holder = FilterParameterValidator.validatePaginationParameters(page, size);
        if (holder.hasMessages()) {
            throw new IncorrectParameterException(holder);
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        return dao.findAll(pageRequest);

    }

    @Override
    public void removeById(long id) {
        validateId(id);
        Optional<T> foundEntity = dao.findById(id);
        if (!foundEntity.isPresent()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }
        dao.removeById(id);
    }

    public List<T> doFilter(MultiValueMap<String, String> params, int page, int size) {
        ExceptionMessageHolder holder = FilterParameterValidator.validateSortType(params);
        holder.putAll(FilterParameterValidator.validatePaginationParameters(page, size).getMessages());
        if (holder.hasMessages()) {
            throw new IncorrectParameterException(holder);
        }
        removeDuplicatePaginationParams(params);
        PageRequest pageRequest = PageRequest.of(page, size);
        return dao.findWithFilter(params, pageRequest);
    }

    private void validateId(long id) {
        ExceptionMessageHolder messageHolder = IdentifiableValidator.validateId(id);
        if (messageHolder.hasMessages()) {
            throw new IncorrectParameterException(messageHolder);
        }
    }

    private void removeDuplicatePaginationParams(MultiValueMap<String, String> params) {
        params.remove("page");
        params.remove("size");
    }
}
