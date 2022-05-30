package com.piatnitsa.validator;

import com.piatnitsa.exception.ExceptionMessageHolder;
import com.piatnitsa.exception.ExceptionMessageKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdentifiableValidatorTest {
    private static final long INCORRECT_ID = -1L;
    private static final long CORRECT_ID = 1L;

    @Test
    void validateCorrectId_thenNoException() {
        ExceptionMessageHolder holder = IdentifiableValidator.validateId(CORRECT_ID);
        assertFalse(holder.hasMessages());
    }

    @Test
    void validateIncorrectId_thenHasException() {
        ExceptionMessageHolder holder = IdentifiableValidator.validateId(INCORRECT_ID);
        assertTrue(holder.hasMessages());
        assertTrue(holder.getMessages().containsKey(ExceptionMessageKey.BAD_ID));
    }
}