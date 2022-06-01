package com.piatnitsa.dao.creator;

import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * This interface is designed for creating queries with restrictions.
 * @param <T> entity return type.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface QueryCreator<T> {
    CriteriaQuery<T> createFilteringGetQuery(MultiValueMap<String, String> params, CriteriaBuilder criteriaBuilder);
}
