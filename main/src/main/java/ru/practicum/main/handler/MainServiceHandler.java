package ru.practicum.main.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.practicum.main.exception.EwmAlreadyExistsException;
import ru.practicum.main.exception.EwmInternalServerException;
import ru.practicum.main.exception.EwmNotFoundException;

@ControllerAdvice
public class MainServiceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EwmAlreadyExistsException.class)
    protected ResponseEntity<Object> handlerAlreadyExistsException(EwmAlreadyExistsException exception,
                                                                   WebRequest request) {
        return handleExceptionInternal(
                exception,
                new EwmError(exception.getMessage()),
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request);
    }

    @ExceptionHandler(value = EwmInternalServerException.class)
    protected ResponseEntity<Object> handlerInternalServerException(EwmInternalServerException exception,
                                                                   WebRequest request) {
        return handleExceptionInternal(
                exception,
                new EwmError(exception.getMessage()),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }


    @ExceptionHandler(value = EwmNotFoundException.class)
    protected ResponseEntity<Object> handlerNotFoundException(EwmInternalServerException exception,
                                                                   WebRequest request) {
        return handleExceptionInternal(
                exception,
                new EwmError(exception.getMessage()),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }
}
