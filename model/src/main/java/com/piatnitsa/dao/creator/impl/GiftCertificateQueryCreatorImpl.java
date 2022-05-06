package com.piatnitsa.dao.creator.impl;

import com.piatnitsa.dao.creator.QueryCreator;
import com.piatnitsa.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GiftCertificateQueryCreatorImpl implements QueryCreator<GiftCertificate> {

    @Override
    public CriteriaQuery<GiftCertificate> createFilteringGetQuery(Map<String, String> params, CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

        List<Predicate> predicates = new ArrayList<>(params.size());
        List<Order> orders = new ArrayList<>(params.size());
        for(Map.Entry<String, String> entry : params.entrySet()) {
            String filterParam = entry.getKey().toLowerCase();
            switch (filterParam) {
                case "name": {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),
                            "%" + entry.getValue().toLowerCase() + "%")
                    );
                    break;
                }
                case "description": {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("description")),
                            "%" + entry.getValue().toLowerCase() + "%")
                    );
                    break;
                }
                case "tag_name": {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.join("tags").get("name")),
                            "%" + entry.getValue().toLowerCase() + "%")
                    );
                    break;
                }
                case "name_sort": {
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
                case "date_sort": {
                    switch (entry.getValue().toLowerCase()) {
                        case "asc": {
                            orders.add(criteriaBuilder.asc(root.get("createDate")));
                            break;
                        }
                        case "desc": {
                            orders.add(criteriaBuilder.desc(root.get("createDate")));
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
