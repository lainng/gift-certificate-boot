package com.piatnitsa.dao;

import com.piatnitsa.entity.GiftCertificate;

import java.util.Optional;

/**
 * This interface describes abstract behavior for working with <code>gift_certificate</code> table in database.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface GiftCertificateDao extends CRUDDao<GiftCertificate> {

    Optional<GiftCertificate> getByName(String name);

}
