package com.piatnitsa.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.piatnitsa.config.language.ExceptionMessageTranslator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public ResponseEntity<ErrorResponse> handleIncorrectParameterException(IncorrectParameterException ex) {
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
        ErrorResponse errorResponse = createResponse(HttpStatus.BAD_REQUEST, details.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEntityException(DuplicateEntityException ex) {
        ErrorResponse errorResponse = createResponse(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchEntityException(NoSuchEntityException ex) {
        ErrorResponse errorResponse = createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<ErrorResponse> methodNotAllowedExceptionException() {
        ErrorResponse errorResponse = createResponse(HttpStatus.METHOD_NOT_ALLOWED, "exception.notSupported");
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            JsonProcessingException.class,
            HttpMessageNotReadableException.class
    })
    public final ResponseEntity<ErrorResponse> handleBadRequestExceptions() {
        ErrorResponse errorResponse = createResponse(HttpStatus.BAD_REQUEST, "exception.badRequest");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<ErrorResponse> handleBadRequestException() {
        ErrorResponse errorResponse = createResponse(HttpStatus.NOT_FOUND, "exception.noHandler");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private ErrorResponse createResponse(HttpStatus status, String messageCode) {
        ErrorResponse errorResponse = new ErrorResponse();
        String details = ExceptionMessageTranslator.toLocale(messageCode);
        errorResponse.setErrorMessage(details);
        errorResponse.setErrorCode(status.toString());
        return errorResponse;
    }
}
