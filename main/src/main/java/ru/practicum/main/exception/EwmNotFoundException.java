package ru.practicum.main.exception;

public class EwmNotFoundException extends RuntimeException {
    public EwmNotFoundException(String message) {
        super(message);
    }
}
