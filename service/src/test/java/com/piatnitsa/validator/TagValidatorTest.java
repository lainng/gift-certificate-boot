package com.piatnitsa.validator;

import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.ExceptionMessageHolder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagValidatorTest {
    private static final String INCORRECT_NAME = "no";
    private static final String CORRECT_NAME = "correct_name";
    private final Tag CORRECT_TAG = new Tag(1, CORRECT_NAME);
    private final Tag INCORRECT_TAG = new Tag(1, INCORRECT_NAME);

    @Test
    void validateCorrectTag_thenNoException() {
        ExceptionMessageHolder holder = TagValidator.validate(CORRECT_TAG);
        assertFalse(holder.hasMessages());
    }

    @Test
    void validateIncorrectTag_thenHasException() {
        ExceptionMessageHolder holder = TagValidator.validate(INCORRECT_TAG);
        assertTrue(holder.hasMessages());
    }

    @Test
    void validateCorrectName_thenNoException() {
        ExceptionMessageHolder holder = TagValidator.validateName(CORRECT_NAME);
        assertFalse(holder.hasMessages());
    }

    @Test
    void validateIncorrectName_thenHasException() {
        ExceptionMessageHolder holder = TagValidator.validateName(INCORRECT_NAME);
        assertTrue(holder.hasMessages());
    }
}