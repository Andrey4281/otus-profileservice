package com.example.profileservice.web.errorHandler;

import com.example.profileservice.exceptions.DuplicateUserException;
import com.example.profileservice.exceptions.NotFoundUserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {NotFoundUserException.class})
    protected ResponseEntity<Object> handleUserNotFoundError(
            RuntimeException ex, WebRequest request) {
        String responseBody = "User not found";
        return handleExceptionInternal(ex, responseBody,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {DuplicateUserException.class})
    protected ResponseEntity<Object> handleUserDuplicateError(
            RuntimeException ex, WebRequest request) {
        String responseBody = "Duplication user by Login";
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
