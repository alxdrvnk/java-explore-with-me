package ru.practicum.main.exception;

public class EwmAlreadyExistsException extends RuntimeException {
    public EwmAlreadyExistsException(String message) {
        super(message);
    }
}
