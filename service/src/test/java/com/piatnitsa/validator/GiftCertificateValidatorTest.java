package com.piatnitsa.validator;

import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.ExceptionMessageHolder;
import com.piatnitsa.exception.ExceptionMessageKey;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GiftCertificateValidatorTest {
    private static final String CORRECT_NAME = "Name";
    private static final String INCORRECT_NAME = "Na";
    private static final String CORRECT_DESCRIPTION = "Description";
    private static final String INCORRECT_DESCRIPTION = "Desc";
    private static final BigDecimal CORRECT_PRICE = new BigDecimal("99.99");
    private static final BigDecimal INCORRECT_PRICE = new BigDecimal("9999999.999");
    private static final int CORRECT_DURATION = 2;
    private static final int INCORRECT_DURATION = 999;
    private final List<Tag> CORRECT_TAGS = Arrays.asList(new Tag(1, "tag1"), new Tag(2, "tag2"));
    private final List<Tag> INCORRECT_TAGS = Arrays.asList(new Tag(1, ""), new Tag(2, ""));

    private final GiftCertificate CORRECT_CERTIFICATE = new GiftCertificate(1L, CORRECT_NAME, CORRECT_DESCRIPTION,
            CORRECT_PRICE, CORRECT_DURATION, null, null, CORRECT_TAGS);
    private final GiftCertificate INCORRECT_CERTIFICATE = new GiftCertificate(1L, INCORRECT_NAME, INCORRECT_DESCRIPTION,
            INCORRECT_PRICE, INCORRECT_DURATION, null, null, INCORRECT_TAGS);
    private final GiftCertificate INCORRECT_CERTIFICATE_FOR_UPDATE = new GiftCertificate(-1L, CORRECT_NAME,
            INCORRECT_DESCRIPTION, CORRECT_PRICE, INCORRECT_DURATION,
            null, null, INCORRECT_TAGS);
    private final GiftCertificate EMPTY_TAGS_CERTIFICATE = new GiftCertificate(1L, CORRECT_NAME, CORRECT_DESCRIPTION,
            CORRECT_PRICE, CORRECT_DURATION, null, null, Collections.emptyList());
    private final GiftCertificate NULL_FIELDS_CERTIFICATE = new GiftCertificate(1L, null, null,
            null, 0, null, null, null);

    @Test
    void validateCorrectCertificate_thenNoExceptions() {
        ExceptionMessageHolder holder = GiftCertificateValidator.validate(CORRECT_CERTIFICATE);
        assertFalse(holder.hasMessages());
    }

    @Test
    void validateIncorrectCertificate_thenHasExceptions() {
        ExceptionMessageHolder holder = GiftCertificateValidator.validate(INCORRECT_CERTIFICATE);
        assertTrue(holder.hasMessages());
        Map<String, Object[]> exceptionMsg = holder.getMessages();
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_NAME));
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_DESCRIPTION));
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_PRICE));
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_DURATION));
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_TAG_NAME + 0));
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_TAG_NAME + 1));
    }

    @Test
    void validateCertificateWithEmptyTags_thenNoException() {
        ExceptionMessageHolder holder = GiftCertificateValidator.validate(EMPTY_TAGS_CERTIFICATE);
        assertFalse(holder.hasMessages());
    }

    @Test
    void validateCertificateWithNullTags_thenNoException() {
        EMPTY_TAGS_CERTIFICATE.setTags(null);
        ExceptionMessageHolder holder = GiftCertificateValidator.validate(EMPTY_TAGS_CERTIFICATE);
        assertFalse(holder.hasMessages());
    }

    @Test
    void validateNullCertificate_thenHasExceptions() {
        ExceptionMessageHolder holder = GiftCertificateValidator.validate(NULL_FIELDS_CERTIFICATE);
        assertTrue(holder.hasMessages());
        Map<String, Object[]> exceptionMsg = holder.getMessages();
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_NAME));
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_DESCRIPTION));
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_PRICE));
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_DURATION));
    }

    @Test
    void validateForUpdateCertificate_thenNoExceptions() {
        ExceptionMessageHolder holder = GiftCertificateValidator.validateForUpdate(CORRECT_CERTIFICATE);
        assertFalse(holder.hasMessages());
    }

    @Test
    void validateForUpdateIncorrectCertificate_thenHasExceptions() {
        ExceptionMessageHolder holder = GiftCertificateValidator.validateForUpdate(INCORRECT_CERTIFICATE_FOR_UPDATE);
        assertTrue(holder.hasMessages());
        Map<String, Object[]> exceptionMsg = holder.getMessages();
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_ID));
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_DESCRIPTION));
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_DURATION));
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_TAG_NAME + 0));
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_TAG_NAME + 1));
    }

    @Test
    void validateCorrectName_thenNoException() {
        ExceptionMessageHolder holder = new ExceptionMessageHolder();
        GiftCertificateValidator.validateName(CORRECT_NAME, holder);
        assertFalse(holder.hasMessages());
    }

    @Test
    void validateIncorrectName_thenHasException() {
        ExceptionMessageHolder holder = new ExceptionMessageHolder();
        GiftCertificateValidator.validateName(INCORRECT_NAME, holder);
        assertTrue(holder.hasMessages());
        assertTrue(holder.getMessages().containsKey(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_NAME));
    }
}