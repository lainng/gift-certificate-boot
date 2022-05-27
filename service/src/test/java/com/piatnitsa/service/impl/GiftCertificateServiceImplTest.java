package com.piatnitsa.service.impl;

import com.piatnitsa.dao.impl.GiftCertificateDaoImpl;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.NoSuchEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    GiftCertificateDaoImpl certificateDao;

    @InjectMocks
    GiftCertificateServiceImpl certificateService;

    private static final long NOT_EXISTED_ID = 999L;
    private final LocalDateTime UPDATED_DATE = LocalDateTime.parse("2018-10-20T07:20:15.156");
    private static final String NOT_EXISTED_NAME = "not existed name";
    private static final String INCORRECT_FILTER_PARAM = "incorrectParameter";
    private static final String INCORRECT_FILTER_VALUE = "incorrectParameterValue";
    private static final String PART_OF_CERTIFICATE_NAME = "giftCertificate";
    private static final String PART_OF_DESCRIPTION = "description";
    private static final String TAG_3_NAME = "tagName3";
    private static final String TAG_4_NAME = "tagName4";
    private static final String ASCENDING = "ASC";
    private static final int PAGE = 0;
    private static final int SIZE = 5;

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

    @Test
    void getById_thenOk() {
        Mockito.when(certificateDao.getById(GIFT_CERTIFICATE_1.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_1));
        GiftCertificate actual = certificateService.getById(GIFT_CERTIFICATE_1.getId());
        assertEquals(GIFT_CERTIFICATE_1, actual);
    }

    @Test
    void getByNotExistedId_thenThrowEx() {
        Mockito.when(certificateDao.getById(NOT_EXISTED_ID)).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> certificateService.getById(NOT_EXISTED_ID));
    }

    @Test
    void getAll_thenOk() {
        Pageable pageRequest = PageRequest.of(0, 5);
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        Mockito.when(certificateDao.getAll(pageRequest)).thenReturn(expected);
        List<GiftCertificate> actual = certificateService.getAll(PAGE, SIZE);
        assertEquals(expected, actual);
    }

    @Test
    void doFilter() {
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void getByName() {
    }
}