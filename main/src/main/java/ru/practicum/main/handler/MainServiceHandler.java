package ru.practicum.main.handler;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestControllerAdvice
public class MainServiceHandler {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(value = EwmAlreadyExistsException.class)
    protected ResponseEntity<Object> handlerAlreadyExistsException(EwmAlreadyExistsException exception,
                                                                   WebRequest request) {
        log.error("Error: {}", exception.getMessage(), exception);
        return makeErrorMessage(HttpStatus.CONFLICT, "Already Exists", exception.getMessage());
    }

    @ExceptionHandler(value = EwmInternalServerException.class)
    protected ResponseEntity<Object> handlerInternalServerException(EwmInternalServerException exception,
                                                                    WebRequest request) {
        log.error("Error: {}", exception.getMessage(), exception);
        return makeErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", exception.getMessage());
    }


    @ExceptionHandler(value = EwmNotFoundException.class)
    protected ResponseEntity<Object> handlerNotFoundException(EwmNotFoundException exception,
                                                              WebRequest request) {
        log.error("Error: {}", exception.getMessage(), exception);
        return makeErrorMessage(HttpStatus.NOT_FOUND, "Object not found", exception.getMessage());
    }

    @ExceptionHandler(value = EwmIllegalArgumentException.class)
    protected ResponseEntity<Object> handlerIllegalArgumentException(EwmIllegalArgumentException exception,
                                                                     WebRequest request) {
        log.error("Error: {}", exception.getMessage(), exception);
        return makeErrorMessage(HttpStatus.CONFLICT, "For the requested operation the conditions are not met",
                exception.getMessage());
    }

    @ExceptionHandler(value = ValidationException.class)
    protected ResponseEntity<Object> handlerValidationException(ValidationException exception,
                                                                WebRequest request) {
        log.error("Error: {}", exception.getMessage(), exception);
        return makeErrorMessage(HttpStatus.BAD_REQUEST, "Incorrect request", exception.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                            WebRequest request) {
        log.error("Error: {}", exception.getMessage(), exception);
        return makeErrorMessage(HttpStatus.BAD_REQUEST, "Incorrect request", exception.getMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handlerMissingServletRequestParameterException(
            MissingServletRequestParameterException exception,
            WebRequest request) {
        log.error("Error: {}", exception.getMessage(), exception);
        return makeErrorMessage(HttpStatus.BAD_REQUEST, "Incorrect request", exception.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handlerHttpMessageNotReadableException(HttpMessageNotReadableException exception,
                                                                            WebRequest request) {
        log.error("Error: {}", exception.getMessage(), exception);
        return makeErrorMessage(HttpStatus.BAD_REQUEST, "Incorrect request", exception.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handlerHttpMessageNotReadableException(Exception exception,
                                                                            WebRequest request) {
        log.error("Error: {}", exception.getMessage(), exception);
        return makeErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error",
                exception.getMessage());
    }

    private ResponseEntity<Object> makeErrorMessage(HttpStatus status, String reason, String message) {
        return new ResponseEntity<>(
                EwmError.builder()
                        .status(status.name())
                        .reason(reason)
                        .error(message)
                        .timestamp(LocalDateTime.now().format(formatter))
                        .build(),
                new HttpHeaders(),
                status);
    }
}
