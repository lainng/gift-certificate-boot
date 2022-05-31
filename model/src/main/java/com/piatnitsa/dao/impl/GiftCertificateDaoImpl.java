package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.GiftCertificateDao;
import com.piatnitsa.dao.creator.QueryCreator;
import com.piatnitsa.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate> implements GiftCertificateDao {
    private static final String SELECT_BY_NAME = "select c from GiftCertificate c where c.name = :name";

    @Autowired
    public GiftCertificateDaoImpl(QueryCreator<GiftCertificate> queryCreator) {
        super(queryCreator, GiftCertificate.class);
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return entityManager.createQuery(SELECT_BY_NAME, GiftCertificate.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }
}
