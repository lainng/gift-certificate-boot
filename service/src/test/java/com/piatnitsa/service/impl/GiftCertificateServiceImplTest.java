package com.piatnitsa.service.impl;

import com.piatnitsa.dao.creator.FilterParameter;
import com.piatnitsa.dao.impl.GiftCertificateDaoImpl;
import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.DuplicateEntityException;
import com.piatnitsa.exception.NoSuchEntityException;
import com.piatnitsa.util.TimestampHandler;
import com.piatnitsa.util.updater.impl.GiftCertificateDataUpdaterImpl;
import com.piatnitsa.util.updater.impl.TagDataUpdaterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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

    GiftCertificateDaoImpl certificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
    TimestampHandler timestampHandler = Mockito.mock(TimestampHandler.class);
    TagDataUpdaterImpl tagDataUpdater = Mockito.mock(TagDataUpdaterImpl.class);
    GiftCertificateDataUpdaterImpl certificateDataUpdater = Mockito.mock(GiftCertificateDataUpdaterImpl.class);

    GiftCertificateServiceImpl certificateService;

    @BeforeEach
    void setUp() {
        certificateService = new GiftCertificateServiceImpl(
                certificateDao,
                certificateDataUpdater,
                tagDataUpdater,
                timestampHandler
        );
    }

    private static final long NOT_EXISTED_ID = 999L;
    private final LocalDateTime UPDATED_DATE = LocalDateTime.parse("2019-10-20T07:20:15.156");
    private static final String NOT_EXISTED_NAME = "not existed name";
    private static final String PART_OF_CERTIFICATE_NAME = "giftCertificate1";
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

    private final GiftCertificate NEW_ADDED_CERTIFICATE = new GiftCertificate(0, "giftCertificate3",
            "description3", new BigDecimal("100.99"), 3, null, null,
            Arrays.asList(new Tag(0, "tagName3"), new Tag(0, "tagName4")));

    private final GiftCertificate BEFORE_INSERT_CERTIFICATE = new GiftCertificate(0, "giftCertificate3",
            "description3", new BigDecimal("100.99"), 3,
            LocalDateTime.parse("2019-10-20T07:20:15.156"), LocalDateTime.parse("2019-10-20T07:20:15.156"),
            Arrays.asList(new Tag(2, "tagName3"), new Tag(4, "tagName4")));

    private final GiftCertificate NEW_DATA_CERTIFICATE = new GiftCertificate(0, "giftCertificate22",
            "description22", new BigDecimal("9999.99"), 22,
            LocalDateTime.parse("2018-10-20T07:20:15.156"), LocalDateTime.parse("2018-10-20T07:20:15.156"),
            Collections.singletonList(new Tag(0, "tagName4")));

    private final GiftCertificate BEFORE_UPDATE_CERTIFICATE = new GiftCertificate(3, "giftCertificate22",
            "description22", new BigDecimal("9999.99"), 22,
            LocalDateTime.parse("2018-10-20T07:20:15.156"), LocalDateTime.parse("2019-10-20T07:20:15.156"),
            Collections.singletonList(new Tag(2, "tagName4")));

    @Test
    void getById_thenOk() {
        Mockito.when(certificateDao.findById(GIFT_CERTIFICATE_1.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_1));
        GiftCertificate actual = certificateService.getById(GIFT_CERTIFICATE_1.getId());
        assertEquals(GIFT_CERTIFICATE_1, actual);
    }

    @Test
    void getByNotExistedId_thenThrowEx() {
        Mockito.when(certificateDao.findById(NOT_EXISTED_ID)).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> certificateService.getById(NOT_EXISTED_ID));
    }

    @Test
    void getAll_thenOk() {
        Pageable pageRequest = PageRequest.of(0, 5);
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        Mockito.when(certificateDao.findAll(pageRequest)).thenReturn(expected);
        List<GiftCertificate> actual = certificateService.getAll(PAGE, SIZE);
        assertEquals(expected, actual);
    }

    @Test
    void doFilter_thenOk() {
        Pageable pageRequest = PageRequest.of(0, 5);

        MultiValueMap<String, String> filterParams = new LinkedMultiValueMap<>();
        filterParams.add(FilterParameter.NAME, PART_OF_CERTIFICATE_NAME);
        filterParams.add(FilterParameter.NAME_SORT, ASCENDING);

        List<GiftCertificate> expected = Collections.singletonList(GIFT_CERTIFICATE_1);
        Mockito.when(certificateDao.findWithFilter(filterParams, pageRequest)).thenReturn(expected);
        List<GiftCertificate> actual = certificateService.doFilter(filterParams, PAGE, SIZE);
        assertEquals(expected, actual);
    }

    @Test
    void insert_thenOk() {
        List<Tag> newTags = Arrays.asList(new Tag(2, TAG_3_NAME), new Tag(4, TAG_4_NAME));
        Mockito.when(tagDataUpdater.updateDataList(NEW_ADDED_CERTIFICATE.getTags())).thenReturn(newTags);
        Mockito.when(certificateDao.findByName(GIFT_CERTIFICATE_2.getName())).thenReturn(Optional.empty());
        Mockito.when(certificateDao.insert(BEFORE_INSERT_CERTIFICATE)).thenReturn(GIFT_CERTIFICATE_2);
        Mockito.when(timestampHandler.getCurrentTimestamp()).thenReturn(UPDATED_DATE);
        GiftCertificate actual = certificateService.insert(NEW_ADDED_CERTIFICATE);
        assertEquals(GIFT_CERTIFICATE_2, actual);
    }

    @Test
    void insertExistedCertificate_thenThrowEx() {
        Mockito.when(certificateDao.findByName(GIFT_CERTIFICATE_2.getName())).thenReturn(Optional.of(GIFT_CERTIFICATE_2));
        assertThrows(DuplicateEntityException.class, () -> certificateService.insert(NEW_ADDED_CERTIFICATE));
    }

    @Test
    void update_thenOk() {
        Mockito.when(certificateDao.findById(GIFT_CERTIFICATE_3.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_3));
        Mockito.when(certificateDataUpdater.updateData(GIFT_CERTIFICATE_3, NEW_DATA_CERTIFICATE)).thenReturn(BEFORE_UPDATE_CERTIFICATE);
        Mockito.when(certificateDao.update(BEFORE_UPDATE_CERTIFICATE)).thenReturn(BEFORE_UPDATE_CERTIFICATE);
        GiftCertificate actual = certificateService.update(GIFT_CERTIFICATE_3.getId(), NEW_DATA_CERTIFICATE);
        assertEquals(BEFORE_UPDATE_CERTIFICATE, actual);
    }

    @Test
    void updateNotExistedEntity_thenThrowEx() {
        Mockito.when(certificateDao.findById(GIFT_CERTIFICATE_3.getId())).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> certificateService.update(
                GIFT_CERTIFICATE_3.getId(),
                NEW_DATA_CERTIFICATE)
        );
    }

    @Test
    void getByName_thenOk() {
        Mockito.when(certificateDao.findByName(GIFT_CERTIFICATE_1.getName())).thenReturn(Optional.of(GIFT_CERTIFICATE_1));
        GiftCertificate actual = certificateService.getByName(GIFT_CERTIFICATE_1.getName());
        assertEquals(GIFT_CERTIFICATE_1, actual);
    }

    @Test
    void getByNotExistedName_thenThrowEx() {
        Mockito.when(certificateDao.findByName(NOT_EXISTED_NAME)).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> certificateService.getByName(NOT_EXISTED_NAME));
    }
}