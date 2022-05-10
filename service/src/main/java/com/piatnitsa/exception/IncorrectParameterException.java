package com.piatnitsa.exception;

/**
 * This class represents the exception that is thrown when the validation of entity parameters fails.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class IncorrectParameterException extends RuntimeException {
    private ExceptionMessage exceptionMessage;

    public IncorrectParameterException() {
    }

    public IncorrectParameterException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public IncorrectParameterException(String messageCode) {
        super(messageCode);
    }

    public IncorrectParameterException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public IncorrectParameterException(Throwable cause) {
        super(cause);
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }
}
