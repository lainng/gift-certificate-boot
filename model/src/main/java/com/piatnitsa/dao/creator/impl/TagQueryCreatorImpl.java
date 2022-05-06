package com.piatnitsa.dao.creator.impl;

import com.piatnitsa.dao.creator.QueryCreator;
import com.piatnitsa.entity.Tag;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TagQueryCreatorImpl implements QueryCreator<Tag> {

    @Override
    public CriteriaQuery<Tag> createFilteringGetQuery(Map<String, String> params, CriteriaBuilder criteriaBuilder) {
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
        return criteriaQuery;
    }
}
