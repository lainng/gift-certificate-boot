package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.GiftCertificateDao;
import com.piatnitsa.dao.creator.QueryCreator;
import com.piatnitsa.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate> implements GiftCertificateDao {
    private final QueryCreator<GiftCertificate> queryCreator;

    @Autowired
    public GiftCertificateDaoImpl(QueryCreator<GiftCertificate> queryCreator) {
        super(GiftCertificate.class);
        this.queryCreator = queryCreator;
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate item) {
        return entityManager.merge(item);
    }

    @Override
    public List<GiftCertificate> getWithFilter(Map<String, String> params) {
        CriteriaQuery<GiftCertificate> criteriaQuery = queryCreator.createFilteringGetQuery(
                params,
                entityManager.getCriteriaBuilder()
        );
        return entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .distinct()
                .collect(Collectors.toList());
    }
}
