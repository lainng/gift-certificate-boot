package com.piatnitsa.exception;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMessageHolder {
    private final Map<String, Object[]> messages = new HashMap<>();

    public ExceptionMessageHolder() {}

    public ExceptionMessageHolder(String exceptionMessageKey, Object... arguments) {
        messages.put(exceptionMessageKey, arguments);
    }

    public Map<String, Object[]> getMessages() {
        return messages;
    }

    public void putException(String exceptionMessageKey, Object... arguments) {
        messages.put(exceptionMessageKey, arguments);
    }

    public boolean isEmpty() {
        return messages.isEmpty();
    }
}
