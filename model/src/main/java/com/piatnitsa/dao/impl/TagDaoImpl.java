package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.TagDao;
import com.piatnitsa.dao.creator.QueryCreator;
import com.piatnitsa.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {
    private static final String QUERY_SELECT_BY_NAME = "select t from Tag t where t.name = :name";

    @Autowired
    public TagDaoImpl(QueryCreator<Tag> queryCreator) {
        super(queryCreator, Tag.class);
    }

    @Override
    public Optional<Tag> getByName(String name) {
        return entityManager.createQuery(QUERY_SELECT_BY_NAME, Tag.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }
}
