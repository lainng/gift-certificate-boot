package com.piatnitsa.exception;

/**
 * This class represents the exception that is thrown when an entity already exists in the database.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
public class DuplicateEntityException extends RuntimeException{

    public DuplicateEntityException() {
    }

    public DuplicateEntityException(String messageCode) {
        super(messageCode);
    }

    public DuplicateEntityException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public DuplicateEntityException(Throwable cause) {
        super(cause);
    }

}
