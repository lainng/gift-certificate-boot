package com.piatnitsa.dao.impl;

import com.piatnitsa.config.DaoTestConfig;
import com.piatnitsa.dao.GiftCertificateDao;
import com.piatnitsa.dao.creator.FilterParameter;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = DaoTestConfig.class)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@Transactional
class GiftCertificateDaoImplTest {
    private static final long NOT_EXISTED_ID = 999L;
    private static final String NOT_EXISTED_NAME = "not existed name";
    private static final String INCORRECT_FILTER_PARAM = "incorrectParameter";
    private static final String INCORRECT_FILTER_PARAM_VALUE = "incorrectParameterValue";
    private static final String PART_OF_CERTIFICATE_NAME = "giftCertificate";
    private static final String PART_OF_DESCRIPTION = "description";
    private static final String TAG_3_NAME = "tagName3";
    private static final String TAG_4_NAME = "tagName4";
    private static final String ASCENDING = "ASC";
    private final Pageable pageRequest = PageRequest.of(0, 5);

    private final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1, "giftCertificate1",
            "description1", new BigDecimal("99.90"), 1,
            LocalDateTime.parse("2020-10-20T07:20:15.156"), LocalDateTime.parse("2020-10-20T07:20:15.156"),
            Collections.singletonList(new Tag(2, "tagName3")));

    private final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2, "giftCertificate3",
            "description3", new BigDecimal("100.99"), 3,
            LocalDateTime.parse("2019-10-20T07:20:15.156"), LocalDateTime.parse("2019-10-20T07:20:15.156"),
            Arrays.asList(new Tag(2, "tagName3"), new Tag(4, "tagName4")));

    private final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3, "giftCertificate2",
            "description2", new BigDecimal("999.99"), 2,
            LocalDateTime.parse("2018-10-20T07:20:15.156"), LocalDateTime.parse("2018-10-20T07:20:15.156"),
            Arrays.asList(new Tag(4, "tagName4"), new Tag(2, "tagName3")));

    @Autowired
    GiftCertificateDao certificateDao;

    @Test
    void getById_thenOk() {
        Optional<GiftCertificate> expected = Optional.of(GIFT_CERTIFICATE_1);
        Optional<GiftCertificate> actual = certificateDao.findById(GIFT_CERTIFICATE_1.getId());
        assertEquals(expected, actual);
    }

    @Test
    void getByNotExistedId_thenReturnNull() {
        Optional<GiftCertificate> actual = certificateDao.findById(NOT_EXISTED_ID);
        assertFalse(actual.isPresent());
    }

    @Test
    void getAll_thenOk() {
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        List<GiftCertificate> actual = certificateDao.findAll(pageRequest);
        assertEquals(expected, actual);
    }

    @Test
    void getWithFilter_thenOk() {
        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();
        filterParams.add(FilterParameter.NAME, PART_OF_CERTIFICATE_NAME);
        filterParams.add(FilterParameter.DESCRIPTION, PART_OF_DESCRIPTION);
        filterParams.put(FilterParameter.TAG_NAME, Arrays.asList(TAG_3_NAME, TAG_4_NAME));
        filterParams.add(FilterParameter.DATE_SORT, ASCENDING);

        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_3, GIFT_CERTIFICATE_2);
        List<GiftCertificate> actual = certificateDao.findWithFilter(filterParams, pageRequest);

        assertEquals(expected, actual);
    }

    @Test
    void getWithIncorrectFilter_thenReturnAll() {
        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();
        filterParams.add(INCORRECT_FILTER_PARAM, INCORRECT_FILTER_PARAM_VALUE);
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        List<GiftCertificate> actual = certificateDao.findWithFilter(filterParams, pageRequest);

        assertEquals(expected, actual);
    }

    @Test
    void getByName_thenOk() {
        Optional<GiftCertificate> expected = Optional.of(GIFT_CERTIFICATE_1);
        Optional<GiftCertificate> actual = certificateDao.findByName(GIFT_CERTIFICATE_1.getName());
        assertEquals(expected, actual);
    }

    @Test
    void getByNotExistedName_thenReturnNull() {
        Optional<GiftCertificate> actual = certificateDao.findByName(NOT_EXISTED_NAME);
        assertFalse(actual.isPresent());
    }
}