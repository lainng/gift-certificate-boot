package com.piatnitsa.validator;

import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.ExceptionMessageHolder;
import com.piatnitsa.exception.ExceptionMessageKey;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * This class provides a validator for {@link GiftCertificate} entity.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class GiftCertificateValidator {
    private static final int MAX_LENGTH_NAME = 30;
    private static final int MIN_LENGTH_NAME = 3;
    private static final int MIN_LENGTH_DESCRIPTION = 5;
    private static final int MAX_LENGTH_DESCRIPTION = 200;
    private static final int MAX_SCALE = 2;
    private static final BigDecimal MIN_PRICE = new BigDecimal("0.01");
    private static final BigDecimal MAX_PRICE = new BigDecimal("999999.99");
    private static final int MAX_DURATION = 366;
    private static final int MIN_DURATION = 1;

    /**
     * Validates all fields of an {@link GiftCertificate} entity.
     * @param item an {@link GiftCertificate} entity for validating.
     * @return the {@link ExceptionMessageHolder} object, which may contain the exception messages
     * thrown during {@link GiftCertificate} validation or be empty if no exceptions were thrown.
     */
    public static ExceptionMessageHolder validate(GiftCertificate item) {
        ExceptionMessageHolder exMessage = new ExceptionMessageHolder();
        validateName(item.getName(), exMessage);
        validateDescription(item.getDescription(), exMessage);
        validatePrice(item.getPrice(), exMessage);
        validateDuration(item.getDuration(), exMessage);
        validateListOfTags(item.getTags(), exMessage);
        return exMessage;
    }

    /**
     * Validates exists fields of an {@link GiftCertificate} entity. If some fields do not contain values, validation is not interrupted.
     * @param item an {@link GiftCertificate} entity for validating.
     * @return the {@link ExceptionMessageHolder} object, which may contain the exception messages
     * thrown during {@link GiftCertificate} validation or be empty if no exceptions were thrown.
     */
    public static ExceptionMessageHolder validateForUpdate(GiftCertificate item) {
        ExceptionMessageHolder exMessages = IdentifiableValidator.validateId(item.getId());

        if (item.getName() != null) {
            validateName(item.getName(), exMessages);
        }
        if (item.getDescription() != null) {
            validateDescription(item.getDescription(), exMessages);
        }
        if (item.getPrice() != null) {
            validatePrice(item.getPrice(), exMessages);
        }
        if (item.getDuration() != 0) {
            validateDuration(item.getDuration(), exMessages);
        }
        validateListOfTags(item.getTags(), exMessages);
        return exMessages;
    }

    /**
     * Validates {@link GiftCertificate} name.
     * @param name name to be validated.
     * @param holder the {@link ExceptionMessageHolder} object, which may contain the exception messages
     * thrown during {@link GiftCertificate} validation or be empty if no exceptions were thrown.
     */
    public static void validateName(String name, ExceptionMessageHolder holder) {
        if (name == null
                || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            holder.putException(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_NAME, name);
        }
    }

    private static void validateListOfTags(List<Tag> tags, ExceptionMessageHolder messageHolder) {
        if (tags == null) return;
        int count = 0;
        for (Tag tag : tags) {
            ExceptionMessageHolder tagMessageHolder = TagValidator.validate(tag);
            if (tagMessageHolder.hasMessages()) {
                Map<String, Object[]> messages = tagMessageHolder.getMessages();
                String updatedMsgKey = ExceptionMessageKey.BAD_TAG_NAME + count;
                messageHolder.getMessages().put(updatedMsgKey, messages.get(ExceptionMessageKey.BAD_TAG_NAME));
                count++;
            }
        }
    }

    private static void validateDescription(String description, ExceptionMessageHolder exMessage) {
        if (description == null
                || description.length() < MIN_LENGTH_DESCRIPTION || description.length() > MAX_LENGTH_DESCRIPTION) {
            exMessage.putException(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_DESCRIPTION, description);
        }
    }

    private static void validatePrice(BigDecimal price, ExceptionMessageHolder exMessage) {
        if (price == null || price.scale() > MAX_SCALE
                || price.compareTo(MIN_PRICE) < 0 || price.compareTo(MAX_PRICE) > 0) {
            exMessage.putException(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_PRICE, price);
        }
    }

    private static void validateDuration(int duration, ExceptionMessageHolder exMessage) {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            exMessage.putException(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_DURATION, duration);
        }
    }
}
