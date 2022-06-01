package com.piatnitsa.util;

import com.piatnitsa.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * This class represents the time handler needed to update
 * the timestamp of the {@link GiftCertificate}.
 * @author Vlad Piatnitsa
 * @version 1.0
 * @see GiftCertificate
 */

@Component
public class TimestampHandler {

    /**
     * Returns the current timestamp in ISO 8601 format.
     * @return current timestamp.
     */
    public LocalDateTime getCurrentTimestamp() {
        return LocalDateTime.now();
    }
}
