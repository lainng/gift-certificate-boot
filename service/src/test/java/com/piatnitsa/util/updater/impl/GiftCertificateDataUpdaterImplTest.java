package com.piatnitsa.util.updater.impl;

import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.util.TimestampHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GiftCertificateDataUpdaterImplTest {

    @Mock TimestampHandler timestampHandler;
    @Mock TagDataUpdaterImpl tagDataUpdater;

    @InjectMocks
    GiftCertificateDataUpdaterImpl certificateDataUpdater;

    private final LocalDateTime UPDATED_TIME = LocalDateTime.parse("2020-10-20T20:20:20.222");
    private final List<Tag> NEW_TAG_LIST = Arrays.asList(new Tag(0, "newTag"), new Tag(0, "oldTag"));
    private final List<Tag> UPDATED_TAG_LIST = Arrays.asList(new Tag(0, "newTag"), new Tag(0, "oldTag"));

    private final GiftCertificate NEW_DATA_CERTIFICATE = new GiftCertificate(0, "newName", "newDescription",
            new BigDecimal("99.99"), 100, null, null, NEW_TAG_LIST);

    private final GiftCertificate SEVERAL_NEW_FIELDS_CERTIFICATE = new GiftCertificate(0, "oldName",
            "newDescription", new BigDecimal("99.99"), 50,
            null, null, NEW_TAG_LIST);

    private final GiftCertificate OLD_DATA_CERTIFICATE = new GiftCertificate(3, "oldName", "oldDescription",
            new BigDecimal("88.88"), 50,
            LocalDateTime.parse("2010-10-20T11:11:11.111"),  LocalDateTime.parse("2010-10-20T11:11:11.111"),
            Collections.singletonList(new Tag(4, "oldTag")));

    private final GiftCertificate UPDATED_CERTIFICATE = new GiftCertificate(3, "newName", "newDescription",
            new BigDecimal("99.99"), 100,
            LocalDateTime.parse("2010-10-20T11:11:11.111"),  UPDATED_TIME, UPDATED_TAG_LIST);

    private final GiftCertificate SEVERAL_NEW_FIELDS_UPDATED_CERTIFICATE = new GiftCertificate(3, "oldName",
            "newDescription", new BigDecimal("99.99"), 50,
            LocalDateTime.parse("2010-10-20T11:11:11.111"),  UPDATED_TIME, UPDATED_TAG_LIST);

    @Test
    void updateData_allFields_thenOk() {
        Mockito.when(tagDataUpdater.updateDataList(NEW_TAG_LIST)).thenReturn(UPDATED_TAG_LIST);
        Mockito.when(timestampHandler.getCurrentTimestamp()).thenReturn(UPDATED_TIME);
        GiftCertificate actual = certificateDataUpdater.updateData(OLD_DATA_CERTIFICATE, NEW_DATA_CERTIFICATE);
        assertEquals(UPDATED_CERTIFICATE, actual);
    }

    @Test
    void updateData_severalFields_thenOk() {
        Mockito.when(tagDataUpdater.updateDataList(NEW_TAG_LIST)).thenReturn(UPDATED_TAG_LIST);
        Mockito.when(timestampHandler.getCurrentTimestamp()).thenReturn(UPDATED_TIME);
        GiftCertificate actual = certificateDataUpdater.updateData(OLD_DATA_CERTIFICATE, SEVERAL_NEW_FIELDS_CERTIFICATE);
        assertEquals(SEVERAL_NEW_FIELDS_UPDATED_CERTIFICATE, actual);
    }

    @Test
    void updateDataList_thenThrowEx() {
        assertThrows(UnsupportedOperationException.class,
                () -> certificateDataUpdater.updateDataList(Collections.emptyList()));
    }
}