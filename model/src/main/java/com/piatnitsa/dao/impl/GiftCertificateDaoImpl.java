package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.GiftCertificateDao;
import com.piatnitsa.dao.creator.QueryCreator;
import com.piatnitsa.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate> implements GiftCertificateDao {

    @Autowired
    public GiftCertificateDaoImpl(QueryCreator<GiftCertificate> queryCreator) {
        super(queryCreator, GiftCertificate.class);
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate item) {
        return entityManager.merge(item);
    }

}
