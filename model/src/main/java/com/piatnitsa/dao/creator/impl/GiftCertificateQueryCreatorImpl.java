package com.piatnitsa.dao.creator.impl;

import com.piatnitsa.dao.creator.AbstractQueryCreator;
import com.piatnitsa.dao.creator.QueryCreator;
import com.piatnitsa.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GiftCertificateQueryCreatorImpl
        extends AbstractQueryCreator<GiftCertificate>
        implements QueryCreator<GiftCertificate> {

    @Override
    public CriteriaQuery<GiftCertificate> createFilteringGetQuery(Map<String, String> params,
                                                                  CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

        List<Predicate> predicates = new ArrayList<>(params.size());
        List<Order> orders = new ArrayList<>(params.size());
        for(Map.Entry<String, String> entry : params.entrySet()) {
            String filterParam = entry.getKey().toLowerCase();
            String paramValue = entry.getValue();
            switch (filterParam) {
                case "name": {
                    predicates.add(addLikePredicate(criteriaBuilder, root.get("name"), paramValue));
                    break;
                }
                case "description": {
                    predicates.add(addLikePredicate(criteriaBuilder, root.get("description"), paramValue));
                    break;
                }
                case "tag_name": {
                    predicates.add(addLikePredicate(criteriaBuilder, root.join("tags").get("name"), paramValue));
                    break;
                }
                case "name_sort": {
                    orders.add(addOrder(criteriaBuilder, root.get("name"), paramValue));
                    break;
                }
                case "date_sort": {
                    orders.add(addOrder(criteriaBuilder, root.get("createDate"), paramValue));
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
