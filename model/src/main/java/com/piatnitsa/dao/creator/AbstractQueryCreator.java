package com.piatnitsa.dao.creator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

public abstract class AbstractQueryCreator<T> {

    protected Predicate addLikePredicate(CriteriaBuilder criteriaBuilder,
                                         Expression<String> expression,
                                         String parameter) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(expression),
                "%" + parameter.toLowerCase() + "%");
    }

    protected Order addOrder(CriteriaBuilder criteriaBuilder,
                             Expression<String> expression,
                             String sortType) {
        Order order = null;
        switch (sortType.toLowerCase()) {
            case "asc": {
                order = criteriaBuilder.asc(expression);
                break;
            }
            case "desc": {
                order = criteriaBuilder.desc(expression);
                break;
            }
        }
        return order;
    }
}
