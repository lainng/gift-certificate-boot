package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.TagDao;
import com.piatnitsa.dao.creator.QueryCreator;
import com.piatnitsa.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {
    private final QueryCreator<Tag> queryCreator;
    private static final String QUERY_SELECT_BY_NAME = "select t from Tag t where t.name = :name";

    @Autowired
    public TagDaoImpl(QueryCreator<Tag> queryCreator) {
        super(Tag.class);
        this.queryCreator = queryCreator;
    }

    @Override
    public Optional<Tag> getByName(String name) {
        return entityManager.createQuery(QUERY_SELECT_BY_NAME, Tag.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<Tag> getWithFilter(Map<String, String> params) {
        CriteriaQuery<Tag> criteriaQuery = queryCreator.createFilteringGetQuery(
                params,
                entityManager.getCriteriaBuilder()
        );
        return entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .collect(Collectors.toList());
    }
}
