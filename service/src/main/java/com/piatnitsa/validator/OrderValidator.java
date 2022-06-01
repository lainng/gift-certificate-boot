package com.piatnitsa.validator;

import com.piatnitsa.entity.Order;
import com.piatnitsa.exception.ExceptionMessageHolder;
import com.piatnitsa.exception.ExceptionMessageKey;

/**
 * This class provides a validator for {@link Order} entity.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class OrderValidator {

    /**
     * Validates {@link Order} entity.
     * @param item object to be validated.
     * @return the {@link ExceptionMessageHolder} object, which may contain the exception messages
     * thrown during {@link Order} validation or be empty if no exceptions were thrown.
     */
    public static ExceptionMessageHolder validate(Order item) {
        ExceptionMessageHolder holder = new ExceptionMessageHolder();

        ExceptionMessageHolder certificateMsgHolder = IdentifiableValidator.validateId(item.getCertificate().getId());
        if (certificateMsgHolder.hasMessages()) {
            holder.putException(ExceptionMessageKey.BAD_GIFT_CERTIFICATE_ID, item.getCertificate().getId());
        }

        ExceptionMessageHolder userMsgHolder = IdentifiableValidator.validateId(item.getUser().getId());
        if (userMsgHolder.hasMessages()) {
            holder.putException(ExceptionMessageKey.BAD_USER_ID, item.getUser().getId());
        }

        return holder;
    }
}
