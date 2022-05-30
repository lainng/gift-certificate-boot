package com.piatnitsa.validator;

import com.piatnitsa.entity.GiftCertificate;
import com.piatnitsa.entity.Order;
import com.piatnitsa.entity.User;
import com.piatnitsa.exception.ExceptionMessageHolder;
import com.piatnitsa.exception.ExceptionMessageKey;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderValidatorTest {
    private static final long INCORRECT_ID = -1L;
    private static final long CORRECT_ID = 1L;

    private final Order CORRECT_ORDER = new Order(0, null, null,
            new GiftCertificate(CORRECT_ID, null, null, null, 0, null, null, null),
            new User(CORRECT_ID, null)
    );
    private final Order INCORRECT_ORDER = new Order(0, null, null,
            new GiftCertificate(INCORRECT_ID, null, null, null, 0, null, null, null),
            new User(INCORRECT_ID, null)
    );
    private final Order ORDER_INCORRECT_USER = new Order(0, null, null,
            new GiftCertificate(CORRECT_ID, null, null, null, 0, null, null, null),
            new User(INCORRECT_ID, null)
    );
    private final Order ORDER_INCORRECT_CERTIFICATE = new Order(0, null, null,
            new GiftCertificate(INCORRECT_ID, null, null, null, 0, null, null, null),
            new User(CORRECT_ID, null)
    );

    @Test
    void validateCorrectOrder_thenNoException() {
        ExceptionMessageHolder holder = OrderValidator.validate(CORRECT_ORDER);
        assertFalse(holder.hasMessages());
    }

    @Test
    void validateIncorrectOrder_thenHasExceptions() {
        ExceptionMessageHolder holder = OrderValidator.validate(INCORRECT_ORDER);
        assertTrue(holder.hasMessages());

        Map<String, Object[]> exceptionMsg = holder.getMessages();
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_USER_ID));
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_ID));
    }

    @Test
    void validateOrderWithIncorrectUserId_thenHasExceptions() {
        ExceptionMessageHolder holder = OrderValidator.validate(ORDER_INCORRECT_USER);
        assertTrue(holder.hasMessages());

        Map<String, Object[]> exceptionMsg = holder.getMessages();
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_USER_ID));
    }

    @Test
    void validateOrderWithIncorrectCertificateId_thenHasExceptions() {
        ExceptionMessageHolder holder = OrderValidator.validate(ORDER_INCORRECT_CERTIFICATE);
        assertTrue(holder.hasMessages());

        Map<String, Object[]> exceptionMsg = holder.getMessages();
        assertTrue(exceptionMsg.containsKey(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_ID));
    }
}