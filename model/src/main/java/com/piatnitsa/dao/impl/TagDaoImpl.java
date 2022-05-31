package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.TagDao;
import com.piatnitsa.dao.creator.FilterParameter;
import com.piatnitsa.dao.creator.QueryCreator;
import com.piatnitsa.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Optional;

@Repository
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {
    private static final String QUERY_SELECT_MOST_POPULAR_TAG = "select t from Order o " +
            "join o.certificate c " +
            "join c.tags t " +
            "group by t.id order by count(t.id) desc, sum(o.cost) desc";

    @Autowired
    public TagDaoImpl(QueryCreator<Tag> queryCreator) {
        super(queryCreator, Tag.class);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add(FilterParameter.TAG_NAME, name);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = queryCreator.createFilteringGetQuery(paramMap, criteriaBuilder);
        return entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<Tag> findMostPopularTagWithHighestCostOfAllOrders() {
        return entityManager.createQuery(QUERY_SELECT_MOST_POPULAR_TAG, entityType)
                .getResultStream()
                .findFirst();
    }
}
