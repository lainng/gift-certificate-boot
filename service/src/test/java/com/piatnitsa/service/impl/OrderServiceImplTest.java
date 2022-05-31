package com.piatnitsa.service.impl;

import com.piatnitsa.dao.impl.GiftCertificateDaoImpl;
import com.piatnitsa.dao.impl.OrderDaoImpl;
import com.piatnitsa.dao.impl.UserDaoImpl;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Order;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.entity.User;
import com.piatnitsa.exception.NoSuchEntityException;
import com.piatnitsa.util.TimestampHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock OrderDaoImpl orderDao;
    @Mock UserDaoImpl userDao;
    @Mock GiftCertificateDaoImpl certificateDao;
    @Mock TimestampHandler timestampHandler;

    @InjectMocks
    OrderServiceImpl orderService;

    private static final long NOT_EXISTED_USER_ID = 999L;
    private static final int PAGE = 0;
    private static final int SIZE = 5;

    private final LocalDateTime UPDATED_DATE = LocalDateTime.parse("2019-10-20T07:20:15.156");
    private final User USER_1 = new User(1, "name1");

    private final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2, "giftCertificate3",
            "description3", new BigDecimal("100.99"), 3,
            LocalDateTime.parse("2019-10-20T07:20:15.156"), LocalDateTime.parse("2019-10-20T07:20:15.156"),
            Arrays.asList(new Tag(2, "tagName3"), new Tag(4, "tagName4")));

    private final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3, "giftCertificate2",
            "description2", new BigDecimal("999.99"), 2,
            LocalDateTime.parse("2018-10-20T07:20:15.156"), LocalDateTime.parse("2018-10-20T07:20:15.156"),
            Arrays.asList(new Tag(4, "tagName4"), new Tag(2, "tagName3")));

    private final Order ORDER_1 = new Order(1, new BigDecimal("10.10"),
            LocalDateTime.parse("2020-10-20T07:20:15.156"), GIFT_CERTIFICATE_3, USER_1);
    private final Order ORDER_2 = new Order(2, new BigDecimal("100.99"),
            LocalDateTime.parse("2019-10-20T07:20:15.156"), GIFT_CERTIFICATE_2, USER_1);
    private final Order INSERT_ORDER = new Order(0, null, null,
            new GiftCertificate(2, null, null, null,
                    0, null, null, null),
            new User(1, null));
    private final Order BEFORE_INSERT_ORDER = new Order(0, new BigDecimal("100.99"),
            LocalDateTime.parse("2019-10-20T07:20:15.156"), GIFT_CERTIFICATE_2, USER_1);
    @Test
    void getOrdersByUserId_thenOk() {
        PageRequest pageRequest = PageRequest.of(PAGE, SIZE);
        List<Order> expected = Arrays.asList(ORDER_1, ORDER_2);
        Mockito.when(userDao.findById(USER_1.getId())).thenReturn(Optional.of(USER_1));
        Mockito.when(orderDao.findByUserId(USER_1.getId(), pageRequest))
                .thenReturn(expected);
        List<Order> actual = orderService.getOrdersByUserId(USER_1.getId(), PAGE, SIZE);
        assertEquals(expected, actual);
    }

    @Test
    void getOrdersByNotExistedUserId_thenOk() {
        Mockito.when(userDao.findById(NOT_EXISTED_USER_ID)).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class,
                () -> orderService.getOrdersByUserId(NOT_EXISTED_USER_ID, PAGE, SIZE));
    }

    @Test
    void insert_thenOk() {
        Mockito.when(userDao.findById(USER_1.getId())).thenReturn(Optional.of(USER_1));
        Mockito.when(certificateDao.findById(GIFT_CERTIFICATE_2.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_2));
        Mockito.when(timestampHandler.getCurrentTimestamp()).thenReturn(UPDATED_DATE);
        Mockito.when(orderDao.insert(BEFORE_INSERT_ORDER)).thenReturn(ORDER_2);
        Order actual = orderService.insert(INSERT_ORDER);
        assertEquals(ORDER_2, actual);
    }

    @Test
    void insertWithNotExistedUser_thenThrow() {
        Mockito.when(certificateDao.findById(GIFT_CERTIFICATE_2.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_2));
        Mockito.when(userDao.findById(USER_1.getId())).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> orderService.insert(INSERT_ORDER));
    }

    @Test
    void insertWithNotExistedCertificate_thenThrow() {
        Mockito.when(certificateDao.findById(GIFT_CERTIFICATE_2.getId())).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> orderService.insert(INSERT_ORDER));
    }
}