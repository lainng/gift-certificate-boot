package com.piatnitsa.dao;

import com.piatnitsa.entity.Order;

import java.util.List;

public interface OrderDao extends CRDDao<Order> {
    List<Order> findByUserId(long userId);
}
