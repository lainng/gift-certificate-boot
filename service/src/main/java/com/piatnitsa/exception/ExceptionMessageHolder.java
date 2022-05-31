package com.piatnitsa.exception;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMessageHolder {
    private final Map<String, Object[]> messages = new HashMap<>();

    public ExceptionMessageHolder() {}

    public Map<String, Object[]> getMessages() {
        return messages;
    }

    public void putException(String exceptionMessageKey, Object... arguments) {
        messages.put(exceptionMessageKey, arguments);
    }

    public boolean hasMessages() {
        return !messages.isEmpty();
    }

    public void putAll(Map<String, Object[]> exceptionMessages) {
        messages.putAll(exceptionMessages);
    }
}
