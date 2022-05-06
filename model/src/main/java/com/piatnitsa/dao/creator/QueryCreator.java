package com.piatnitsa.dao.creator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Map;

public interface QueryCreator<T> {
    CriteriaQuery<T> createFilteringGetQuery(Map<String, String> params, CriteriaBuilder criteriaBuilder);
}
