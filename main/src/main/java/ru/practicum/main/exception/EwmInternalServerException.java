package ru.practicum.main.exception;

public class EwmInternalServerException extends RuntimeException {
    public EwmInternalServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
