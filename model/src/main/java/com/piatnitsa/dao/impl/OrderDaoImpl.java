package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.OrderDao;
import com.piatnitsa.entity.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

@Component
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {
    private static final String QUERY_SELECT_BY_USER_ID = "select o from Order o where o.user.id = :userId";

    public OrderDaoImpl() {
        super(null, Order.class);
    }

    @Override
    public List<Order> findByUserId(long userId) {
        return entityManager.createQuery(QUERY_SELECT_BY_USER_ID, entityType)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Optional<Order> getById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getWithFilter(MultiValueMap<String, String> params) {
        throw new UnsupportedOperationException();
    }
}
