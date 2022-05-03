package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.GiftCertificateDao;
import com.piatnitsa.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate> implements GiftCertificateDao {

    @Autowired
    public GiftCertificateDaoImpl() {
        super(GiftCertificate.class);
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate item) {
        return entityManager.merge(item);
    }

    @Override
    public List<GiftCertificate> getWithFilter(Map<String, String> params) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

        List<Predicate> predicates = new ArrayList<>(params.size());
        if (params.containsKey("name")) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + params.get("name").toLowerCase() + "%")
            );
        }
        if (params.containsKey("description")) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("description")),
                    "%" + params.get("description").toLowerCase() + "%")
            );
        }
        if (params.containsKey("tag_name")) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.join("tags").get("name")),
                    "%" + params.get("tag_name").toLowerCase() + "%")
            );
        }
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));

        if (params.containsKey("name_sort")) {
            String nameSortType = params.get("name_sort").toLowerCase();
            switch (nameSortType) {
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
        if (params.containsKey("date_sort")) {
            String dateSortType = params.get("date_sort").toLowerCase();
            switch (dateSortType) {
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
                .distinct()
                .collect(Collectors.toList());
    }
}
