package com.piatnitsa.dao;

import com.piatnitsa.entity.Tag;

import java.util.Optional;

/**
 * This interface describes abstract behavior for working with <code>tag</code> table in database.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface TagDao extends CRDDao<Tag>{

    /**
     * Retrieves an {@link Tag} entity by its name.
     * @param name entity name.
     * @return an {@link Tag} entity.
     */
    Optional<Tag> getByName(String name);

}
