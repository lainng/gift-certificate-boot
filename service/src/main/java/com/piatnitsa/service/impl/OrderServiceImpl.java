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
import com.piatnitsa.validator.FilterParameterValidator;
import com.piatnitsa.validator.IdentifiableValidator;
import com.piatnitsa.validator.OrderValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

@Component
public class OrderServiceImpl extends AbstractService<Order> implements OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;
    private final TimestampHandler timestampHandler;

    public OrderServiceImpl(OrderDao orderDao,
                            UserDao userDao,
                            GiftCertificateDao giftCertificateDao,
                            TimestampHandler timestampHandler) {
        super(orderDao);
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
        this.timestampHandler = timestampHandler;
    }

    @Override
    public List<Order> getOrdersByUserId(long userId, int page, int size) {
        ExceptionMessageHolder messageHolder = IdentifiableValidator.validateId(userId);
        messageHolder.putAll(FilterParameterValidator.validatePaginationParameters(page, size).getMessages());
        if (messageHolder.hasMessages()) {
            throw new IncorrectParameterException(messageHolder);
        }

        Optional<User> optionalUser = userDao.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new NoSuchEntityException(ExceptionMessageKey.USER_NOT_FOUND);
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return orderDao.findByUserId(userId, pageRequest);
    }

    @Override
    public Order insert(Order entity) {
        ExceptionMessageHolder holder = OrderValidator.validate(entity);
        if (holder.hasMessages()) {
            throw new IncorrectParameterException(holder);
        }

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(entity.getCertificate().getId());
        if (!optionalGiftCertificate.isPresent()) {
            throw new NoSuchEntityException(ExceptionMessageKey.GIFT_CERTIFICATE_NOT_FOUND);
        }
        entity.setCertificate(optionalGiftCertificate.get());

        Optional<User> optionalUser = userDao.findById(entity.getUser().getId());
        if (!optionalUser.isPresent()) {
            throw new NoSuchEntityException(ExceptionMessageKey.USER_NOT_FOUND);
        }
        entity.setUser(optionalUser.get());

        entity.setCost(optionalGiftCertificate.get().getPrice());
        entity.setPurchaseTime(timestampHandler.getCurrentTimestamp());
        return orderDao.insert(entity);
    }

    @Override
    public Order getById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getAll(int page, int size) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> doFilter(MultiValueMap<String, String> params, int page, int size) {
        throw new UnsupportedOperationException();
    }
}
