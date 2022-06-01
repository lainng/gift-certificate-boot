package com.piatnitsa.service;

import com.piatnitsa.entity.Tag;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * This interface describes abstract behavior for working with {@link Tag} objects.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface TagService extends CRDService<Tag> {

    /**
     * Method for getting a list of {@link Tag} by specific parameters.
     * @param params request parameters from URL.
     * @return {@link List} of {@link Tag}.
     */
    List<Tag> doFilter(MultiValueMap<String, String> params, int page, int size);

    /**
     * Method for getting the most popular {@link Tag} entity with the highest cost of all orders.
     * @return the most popular {@link Tag} entity.
     * @see com.piatnitsa.entity.Order
     */
    Tag getMostPopularTagWithHighestCostOfAllOrders();
}
