package ru.practicum.main.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.practicum.main.exception.EwmAlreadyExistsException;
import ru.practicum.main.exception.EwmIllegalArgumentException;
import ru.practicum.main.exception.EwmInternalServerException;
import ru.practicum.main.exception.EwmNotFoundException;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class MainServiceHandler {

    @ExceptionHandler(value = EwmAlreadyExistsException.class)
    protected ResponseEntity<Object> handlerAlreadyExistsException(EwmAlreadyExistsException exception,
                                                                   WebRequest request) {
        return new ResponseEntity<>(
                EwmError.builder()
                        .status(HttpStatus.CONFLICT.name())
                        .reason("Already Exists")
                        .error(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build(),
                new HttpHeaders(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = EwmInternalServerException.class)
    protected ResponseEntity<Object> handlerInternalServerException(EwmInternalServerException exception,
                                                                    WebRequest request) {
        return new ResponseEntity<>(
                EwmError.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .reason("Internal Server Error")
                        .error(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build(),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = EwmNotFoundException.class)
    protected ResponseEntity<Object> handlerNotFoundException(EwmNotFoundException exception,
                                                              WebRequest request) {
        return new ResponseEntity<>(
                EwmError.builder()
                        .status(HttpStatus.NOT_FOUND.name())
                        .reason("Object not found")
                        .error(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EwmIllegalArgumentException.class)
    protected ResponseEntity<Object> handlerIllegalArgumentException(EwmIllegalArgumentException exception,
                                                                     WebRequest request) {
        return new ResponseEntity<>(
                EwmError.builder()
                        .status(HttpStatus.CONFLICT.name())
                        .reason("For the requested operation the conditions are not met")
                        .error(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build(),
                new HttpHeaders(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = ValidationException.class)
    protected ResponseEntity<Object> handlerValidationException(ValidationException exception,
                                                                WebRequest request) {

        return new ResponseEntity<>(
                EwmError.builder()
                        .status(HttpStatus.BAD_REQUEST.name())
                        .reason("Incorrect request")
                        .error(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                            WebRequest request) {
        return new ResponseEntity<>(
                EwmError.builder()
                        .status(HttpStatus.BAD_REQUEST.name())
                        .reason("Incorrect request")
                        .error(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handlerMissingServletRequestParameterException(MissingServletRequestParameterException exception,
                                                                                    WebRequest request) {
        return new ResponseEntity<>(
                EwmError.builder()
                        .status(HttpStatus.BAD_REQUEST.name())
                        .reason("Incorrect request")
                        .error(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handlerHttpMessageNotReadableException(HttpMessageNotReadableException exception,
                                                                            WebRequest request) {
        return new ResponseEntity<>(
                EwmError.builder()
                        .status(HttpStatus.BAD_REQUEST.name())
                        .reason("Incorrect request")
                        .error(exception.getMessage())
                        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }
}
