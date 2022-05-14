package com.piatnitsa.validator;

import com.piatnitsa.exception.ExceptionMessageHolder;
import com.piatnitsa.exception.ExceptionMessageKey;

/**
 * This class provides a validator for entity identifier.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class IdentifiableValidator {

    /**
     * Validates an entity ID.
     * @param id an entity ID.
     * @return the {@link ExceptionMessageHolder} object, which may contain the exception messages thrown during ID validation
     * or be empty if no exceptions were thrown.
     */
    public static ExceptionMessageHolder validateId(long id) {
        ExceptionMessageHolder holder = new ExceptionMessageHolder();
        if (id < 1) {
            holder.putException(ExceptionMessageKey.BAD_ID, id);
        }
        return holder;
    }
}
