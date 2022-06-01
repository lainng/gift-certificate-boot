package com.piatnitsa.service;

import com.piatnitsa.entity.Order;

import java.util.List;

/**
 * This interface describes abstract behavior for working with {@link Order} objects.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public interface OrderService extends CRDService<Order> {

    /**
     * Method for getting a {@link List} of {@link Order} entities by specified user ID which these orders belong.
     * @param userId user ID.
     * @param page page number for pagination.
     * @param size page size for pagination.
     * @return a {@link List} of {@link Order} entities.
     */
    List<Order> getOrdersByUserId (long userId, int page, int size);
}
