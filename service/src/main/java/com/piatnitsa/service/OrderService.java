package com.piatnitsa.service;

import com.piatnitsa.entity.Order;

import java.util.List;

public interface OrderService extends CRDService<Order> {
    List<Order> getOrdersByUserId (long userId);
}
