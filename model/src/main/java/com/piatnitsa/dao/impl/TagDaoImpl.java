package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.TagDao;
import com.piatnitsa.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {
    private static final String QUERY_SELECT_BY_NAME = "select t from Tag t where t.name = :name";

    @Autowired
    public TagDaoImpl() {
        super(Tag.class);
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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);

        List<Predicate> predicates = new ArrayList<>(1);
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(root.get("name")),
                "%" + params.get("tag_name").toLowerCase() + "%")
        );

        criteriaQuery.select(root).where(predicates.toArray(new Predicate[0]));
        if (params.containsKey("tag_name_sort")) {
            String sortType = params.get("tag_name_sort").toLowerCase();
            switch (sortType) {
                case "asc": {
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
                    break;
                }
                case "desc": {
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("name")));
                    break;
                }
            }
        }
        return entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .collect(Collectors.toList());
    }
}
