package com.iit.aiposizi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(final EntityNotFoundException e,
                                                             final WebRequest request) {
        var ex = new UnexpectedError();
        ex.setMessage(e.getMessage());
        return handleExceptionInternal(e, ex.getMessage(), new HttpHeaders(), e.getStatus(), request);
    }

    @ExceptionHandler(InvalidInputDataException.class)
    protected ResponseEntity<Object> handleIncorrectDataException(final InvalidInputDataException e,
                                                                  final WebRequest request) {
        var ex = new UnexpectedError();
        ex.setMessage(e.getMessage());
        return handleExceptionInternal(e, ex, new HttpHeaders(), e.getStatus(), request);
    }

    @ExceptionHandler(IncorrectJwtAuthenticationException.class)
    protected ResponseEntity<Object> handleIncorrectDataException(final IncorrectJwtAuthenticationException e,
                                                                  final WebRequest request) {
        var ex = new UnexpectedError();
        ex.setMessage(e.getMessage());
        return handleExceptionInternal(e, ex, new HttpHeaders(), e.getStatus(), request);
    }

    @ExceptionHandler(EntityAlreadyProcessedException.class)
    protected ResponseEntity<Object> handleIncorrectDataException(final EntityAlreadyProcessedException e,
                                                                  final WebRequest request) {
        var ex = new UnexpectedError();
        ex.setMessage(e.getMessage());
        return handleExceptionInternal(e, ex, new HttpHeaders(), e.getStatus(), request);
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    protected ResponseEntity<Object> handleIncorrectDataException(final ForbiddenAccessException e,
                                                                  final WebRequest request) {
        var ex = new UnexpectedError();
        ex.setMessage(e.getMessage());
        return handleExceptionInternal(e, ex, new HttpHeaders(), e.getStatus(), request);
    }

}
