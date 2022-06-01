package com.piatnitsa.dao;

import com.piatnitsa.entity.Order;
import com.piatnitsa.entity.Tag;

import java.util.Optional;

/**
 * This interface describes abstract behavior for working with <code>tag</code> table in the database.
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
    Optional<Tag> findByName(String name);

    /**
     * Retrieves the most popular {@link Tag} entity with the highest cost of all {@link Order} entities.
     * @return the most popular {@link Tag} entity.
     */
    Optional<Tag> findMostPopularTagWithHighestCostOfAllOrders();

}
