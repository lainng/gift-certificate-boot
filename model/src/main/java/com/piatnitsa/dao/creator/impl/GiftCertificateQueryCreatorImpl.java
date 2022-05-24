package com.piatnitsa.dao.creator.impl;

import com.piatnitsa.dao.creator.AbstractQueryCreator;
import com.piatnitsa.dao.creator.FilterParameter;
import com.piatnitsa.dao.creator.QueryCreator;
import com.piatnitsa.entity.GiftCertificate;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GiftCertificateQueryCreatorImpl
        extends AbstractQueryCreator
        implements QueryCreator<GiftCertificate> {
    private static final String TAGS_FIELD = "tags";
    private static final String NAME_FIELD = "name";
    private static final String CREATE_DATE_FIELD = "createDate";

    @Override
    public CriteriaQuery<GiftCertificate> createFilteringGetQuery(MultiValueMap<String, String> params,
                                                                  CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

        List<Predicate> predicates = new ArrayList<>(params.size());
        List<Order> orders = new ArrayList<>(params.size());
        for(Map.Entry<String, List<String>> entry : params.entrySet()) {
            String filterParam = entry.getKey().toLowerCase();
            String paramValue = entry.getValue()
                    .stream()
                    .findFirst()
                    .orElse("");
            switch (filterParam) {
                case FilterParameter.NAME: {
                    predicates.add(addLikePredicate(criteriaBuilder, root.get(NAME_FIELD), paramValue));
                    break;
                }
                case FilterParameter.DESCRIPTION: {
                    predicates.add(addLikePredicate(criteriaBuilder, root.get(FilterParameter.DESCRIPTION), paramValue));
                    break;
                }
                case FilterParameter.TAG_NAME: {
                    List<String> tagNames = entry.getValue();
                    tagNames.forEach(
                            (tagName) -> predicates.add(
                                    addLikePredicate(
                                            criteriaBuilder, root.join(TAGS_FIELD).get(FilterParameter.NAME),
                                            tagName
                                    )
                            )
                    );
                    break;
                }
                case FilterParameter.NAME_SORT: {
                    orders.add(addOrder(criteriaBuilder, root.get(NAME_FIELD), paramValue));
                    break;
                }
                case FilterParameter.DATE_SORT:   {
                    orders.add(addOrder(criteriaBuilder, root.get(CREATE_DATE_FIELD), paramValue));
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
