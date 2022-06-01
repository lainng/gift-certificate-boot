package com.piatnitsa.exception;

/**
 * This class represents the exception that is thrown when entity does not found in the database.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException() {
    }

    public NoSuchEntityException(String messageCode) {
        super(messageCode);
    }

    public NoSuchEntityException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public NoSuchEntityException(Throwable cause) {
        super(cause);
    }
}
