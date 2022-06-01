package com.piatnitsa.validator;

import com.piatnitsa.entity.Tag;
import com.piatnitsa.exception.ExceptionMessageHolder;
import com.piatnitsa.exception.ExceptionMessageKey;

/**
 * This class provides a validator for {@link Tag} entity.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class TagValidator {
    private static final int MAX_LENGTH_NAME = 30;
    private static final int MIN_LENGTH_NAME = 3;

    /**
     * Validates a {@link Tag} entity.
     * @param item a {@link Tag} entity for validating.
     * @return the {@link ExceptionMessageHolder} object, which may contain the exception messages thrown during {@link Tag} validation
     * or be empty if no exceptions were thrown.
     */
    public static ExceptionMessageHolder validate(Tag item) {
        return validateName(item.getName());
    }

    /**
     * Validates a {@link Tag} entity name.
     * @param name a {@link Tag} name.
     * @return the {@link ExceptionMessageHolder} object, which may contain the exception messages thrown during {@link Tag} validation
     * or be empty if no exceptions were thrown.
     */
    public static ExceptionMessageHolder validateName(String name) {
        ExceptionMessageHolder exMessage = new ExceptionMessageHolder();
        if (name == null
                || name.length() < MIN_LENGTH_NAME
                || name.length() > MAX_LENGTH_NAME) {
            exMessage.putException(ExceptionMessageKey.BAD_TAG_NAME, name);
        }
        return exMessage;
    }
}
