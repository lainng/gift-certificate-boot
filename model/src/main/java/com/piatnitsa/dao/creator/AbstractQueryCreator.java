package com.piatnitsa.dao.creator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public abstract class AbstractQueryCreator<T> {

    protected Predicate addTagNamePredicate(Map<String, String> params,
                                         CriteriaBuilder criteriaBuilder,
                                         Root<T> root) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("name")),
                "%" + params.get("tag_name").toLowerCase() + "%");
    }

    protected Predicate addNamePredicate(Map<String, String> params,
                                         CriteriaBuilder criteriaBuilder,
                                         Root<T> root) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("name")),
                "%" + params.get("name").toLowerCase() + "%");
    }
}
