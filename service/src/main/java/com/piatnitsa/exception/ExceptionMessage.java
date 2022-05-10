package com.piatnitsa.exception;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMessage {
    private final Map<String, Object[]> exceptionMessages = new HashMap<>();

    public ExceptionMessage() {}

    public ExceptionMessage(String exceptionMessageKey, Object... arguments) {
        exceptionMessages.put(exceptionMessageKey, arguments);
    }

    public Map<String, Object[]> getExceptionMessages() {
        return exceptionMessages;
    }

    public void putException(String exceptionMessageKey, Object... arguments) {
        exceptionMessages.put(exceptionMessageKey, arguments);
    }

    public boolean isEmpty() {
        return exceptionMessages.isEmpty();
    }
}
