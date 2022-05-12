package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.TagDao;
import com.piatnitsa.dao.creator.FilterParameter;
import com.piatnitsa.dao.creator.QueryCreator;
import com.piatnitsa.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {

    @Autowired
    public TagDaoImpl(QueryCreator<Tag> queryCreator) {
        super(queryCreator, Tag.class);
    }

    @Override
    public Optional<Tag> getByName(String name) {
        Map<String, String> paramMap = Collections.singletonMap(FilterParameter.TAG_NAME, name);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = queryCreator.createFilteringGetQuery(paramMap, criteriaBuilder);
        return entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .findFirst();
    }
}
