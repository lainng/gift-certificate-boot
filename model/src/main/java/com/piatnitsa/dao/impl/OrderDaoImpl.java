package com.piatnitsa.dao.impl;

import com.piatnitsa.dao.AbstractDao;
import com.piatnitsa.dao.OrderDao;
import com.piatnitsa.entity.Order;
import org.springframework.data.domain.Pageable;
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
    public List<Order> findByUserId(long userId, Pageable pageable) {
        return entityManager.createQuery(QUERY_SELECT_BY_USER_ID, entityType)
                .setParameter("userId", userId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public Optional<Order> findById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> findWithFilter(MultiValueMap<String, String> params, Pageable pageable) {
        throw new UnsupportedOperationException();
    }
}
