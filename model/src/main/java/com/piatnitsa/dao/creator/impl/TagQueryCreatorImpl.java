package com.piatnitsa.dao.creator.impl;

import com.piatnitsa.dao.creator.QueryCreator;
import com.piatnitsa.entity.Tag;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TagQueryCreatorImpl implements QueryCreator<Tag> {

    @Override
    public CriteriaQuery<Tag> createFilteringGetQuery(Map<String, String> params, CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);

        List<Predicate> predicates = new ArrayList<>(params.size());
        List<Order> orders = new ArrayList<>(params.size());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey().toLowerCase();
            switch (key) {
                case "tag_name": {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),
                            "%" + params.get("tag_name").toLowerCase() + "%")
                    );
                    break;
                }
                case "tag_name_sort": {
                    switch (entry.getValue().toLowerCase()) {
                        case "asc": {
                            orders.add(criteriaBuilder.asc(root.get("name")));
                            break;
                        }
                        case "desc": {
                            orders.add(criteriaBuilder.desc(root.get("name")));
                            break;
                        }
                    }
                    break;
                }
            }
        }
        criteriaQuery.select(root)
                .where(predicates.toArray(new Predicate[]{}))
                .orderBy(orders);
        return criteriaQuery;
    }
}
