package com.piatnitsa.dao.creator;

import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public interface QueryCreator<T> {
    CriteriaQuery<T> createFilteringGetQuery(MultiValueMap<String, String> params, CriteriaBuilder criteriaBuilder);
}
