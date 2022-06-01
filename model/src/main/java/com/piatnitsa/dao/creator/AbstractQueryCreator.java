package com.piatnitsa.dao.creator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

/**
 * This class provides tools for creating a query. Contains methods for creating constrains in a query.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public abstract class AbstractQueryCreator {

    /**
     * Creates "like" predicate for the query.
     * @param criteriaBuilder a query builder object.
     * @param expression entity query expression.
     * @param parameter parameter that used in the predicate.
     * @return the "like" query {@link Predicate}.
     */
    protected Predicate addLikePredicate(CriteriaBuilder criteriaBuilder,
                                         Expression<String> expression,
                                         String parameter) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(expression),
                "%" + parameter.toLowerCase() + "%");
    }

    /**
     * Creates a sort constraint for the query.
     * @param criteriaBuilder a query builder object.
     * @param expression entity query expression.
     * @param sortType type of sort.
     * @return a sort constraint object for the query.
     */
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
