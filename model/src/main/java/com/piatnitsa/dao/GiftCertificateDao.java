package com.piatnitsa.dao;

import com.piatnitsa.entity.GiftCertificate;

import java.util.Optional;

/**
 * This interface describes abstract behavior for working with <code>gift_certificate</code> table in the database.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface GiftCertificateDao extends CRUDDao<GiftCertificate> {

    /**
     * Retrieves an {@link GiftCertificate} entity by its name.
     * @param name entity name.
     * @return an {@link GiftCertificate} entity.
     */
    Optional<GiftCertificate> findByName(String name);

}
