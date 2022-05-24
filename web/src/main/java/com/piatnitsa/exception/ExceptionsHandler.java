package com.piatnitsa.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.piatnitsa.config.language.ExceptionMessageTranslator;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;

/**
 * This class presents entity which will be returned from controller in case generating exceptions.
 * @author Vlad Piatnitsa
 * @version 1.0
 */
@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(IncorrectParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectParameterException(IncorrectParameterException ex) {
        StringBuilder details = new StringBuilder();
        for (Map.Entry<String, Object[]> exceptionMsg : ex.getExceptionMessageHolder().getMessages().entrySet()) {
            String msgKey = exceptionMsg.getKey();
            String translatedMsg;
            if (msgKey.matches(ExceptionMessageKey.BAD_TAG_NAME + "\\d+")) {
                translatedMsg = ExceptionMessageTranslator.toLocale(ExceptionMessageKey.BAD_TAG_NAME);
            } else {
                translatedMsg = ExceptionMessageTranslator.toLocale(exceptionMsg.getKey());
            }
            String detail = String.format(translatedMsg, exceptionMsg.getValue());
            details.append(detail).append(' ');
        }
        return createResponse(HttpStatus.BAD_REQUEST, details.toString());
    }

    @ExceptionHandler(DuplicateEntityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateEntityException(DuplicateEntityException ex) {
        return createResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(NoSuchEntityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchEntityException(NoSuchEntityException ex) {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse methodNotAllowedExceptionException() {
        return createResponse(HttpStatus.METHOD_NOT_ALLOWED, "exception.notSupported");
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            JsonProcessingException.class,
            HttpMessageNotReadableException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestExceptions() {
        return createResponse(HttpStatus.BAD_REQUEST, "exception.badRequest");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleBadRequestException() {
        return createResponse(HttpStatus.NOT_FOUND, "exception.noHandler");
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleUnsupportedOperationException() {
        return createResponse(HttpStatus.METHOD_NOT_ALLOWED, "exception.unsupportedOperation");
    }

    private ErrorResponse createResponse(HttpStatus status, String messageCode) {
        ErrorResponse errorResponse = new ErrorResponse();
        String details = ExceptionMessageTranslator.toLocale(messageCode);
        errorResponse.setErrorMessage(details);
        errorResponse.setErrorCode(status.toString());
        return errorResponse;
    }
}
