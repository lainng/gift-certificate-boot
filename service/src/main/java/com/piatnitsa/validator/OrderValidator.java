package com.piatnitsa.validator;

import com.piatnitsa.entity.Order;
import com.piatnitsa.exception.ExceptionMessageHolder;
import com.piatnitsa.exception.ExceptionMessageKey;

public class OrderValidator {
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
