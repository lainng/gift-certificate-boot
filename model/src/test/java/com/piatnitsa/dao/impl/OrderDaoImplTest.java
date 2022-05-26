package com.piatnitsa.dao.impl;

import com.piatnitsa.config.DaoTestConfig;
import com.piatnitsa.dao.OrderDao;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Order;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = DaoTestConfig.class)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@Transactional
class OrderDaoImplTest {
    private static final long NOT_EXISTED_ID = 999L;
    private final Pageable pageRequest = PageRequest.of(0, 5);

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
    private final Order ORDER_2 = new Order(2, new BigDecimal("30.30"),
            LocalDateTime.parse("2019-10-20T07:20:15.156"), GIFT_CERTIFICATE_2, USER_1);

    @Autowired
    OrderDao orderDao;

    @Test
    void findByUserId_thenOk() {
        List<Order> expected = Arrays.asList(ORDER_1, ORDER_2);
        List<Order> actual = orderDao.findByUserId(USER_1.getId(), pageRequest);
        assertEquals(expected, actual);
    }

    @Test
    void findNotExistedUserId_thenReturnNull() {
        List<Order> actual = orderDao.findByUserId(NOT_EXISTED_ID, pageRequest);
        assertTrue(actual.isEmpty());
    }
}