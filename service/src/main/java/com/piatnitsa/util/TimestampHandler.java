package com.piatnitsa.util;

import com.piatnitsa.entity.GiftCertificate;

import java.time.LocalDateTime;

/**
 * This class represents the time handler needed to update
 * the timestamp of the {@link GiftCertificate}.
 * @author Vlad Piatnitsa
 * @version 1.0
 * @see GiftCertificate
 */
public class TimestampHandler {

    /**
     * Returns the current timestamp in ISO 8601 format.
     * @return current timestamp of string type.
     */
    public static LocalDateTime getCurrentTimestamp() {
        return LocalDateTime.now();
    }
}
