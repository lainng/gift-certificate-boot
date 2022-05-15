package com.piatnitsa.service.impl;

import com.piatnitsa.dao.GiftCertificateDao;
import com.piatnitsa.dao.OrderDao;
import com.piatnitsa.dao.UserDao;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Order;
import com.piatnitsa.entity.User;
import com.piatnitsa.exception.ExceptionMessageHolder;
import com.piatnitsa.exception.ExceptionMessageKey;
import com.piatnitsa.exception.IncorrectParameterException;
import com.piatnitsa.exception.NoSuchEntityException;
import com.piatnitsa.service.AbstractService;
import com.piatnitsa.service.OrderService;
import com.piatnitsa.util.TimestampHandler;
import com.piatnitsa.validator.OrderValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderServiceImpl extends AbstractService<Order> implements OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;

    public OrderServiceImpl(OrderDao orderDao,
                            UserDao userDao,
                            GiftCertificateDao giftCertificateDao) {
        super(orderDao);
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public List<Order> getOrdersByUserId(long userId) {
        validateId(userId);
        return orderDao.findByUserId(userId);
    }

    @Override
    public Order insert(Order item) {
        ExceptionMessageHolder holder = OrderValidator.validate(item);
        if (holder.hasMessages()) {
            throw new IncorrectParameterException(holder);
        }

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.getById(item.getCertificate().getId());
        if (!optionalGiftCertificate.isPresent()) {
            throw new NoSuchEntityException(ExceptionMessageKey.GIFT_CERTIFICATE_NOT_FOUND);
        }
        item.setCertificate(optionalGiftCertificate.get());

        Optional<User> optionalUser = userDao.getById(item.getUser().getId());
        if (!optionalUser.isPresent()) {
            throw new NoSuchEntityException(ExceptionMessageKey.USER_NOT_FOUND);
        }
        item.setUser(optionalUser.get());

        item.setCost(optionalGiftCertificate.get().getPrice());
        item.setPurchaseTime(TimestampHandler.getCurrentTimestamp());
        orderDao.insert(item);
        return item;
    }
}
