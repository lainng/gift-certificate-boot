package com.piatnitsa.validator;

import com.piatnitsa.exception.ExceptionMessageKey;
import com.piatnitsa.exception.IncorrectParameterException;

/**
 * This class provides a validator for entity identifier.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class IdentifiableValidator {

    /**
     * Validates an entity ID.
     * @param id an entity ID.
     * @throws IncorrectParameterException if the ID contains incorrect value.
     */
    public static void validateId(long id) throws IncorrectParameterException {
        if (id < 1) {
            throw new IncorrectParameterException(ExceptionMessageKey.BAD_ID);
        }
    }
}
