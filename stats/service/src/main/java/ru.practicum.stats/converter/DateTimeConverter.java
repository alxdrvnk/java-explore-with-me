package ru.practicum.stats.converter;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeConverter {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LocalDateTime parseDate(String date) {
        return LocalDateTime.parse(date, formatter);
    }

    public String formatDate(LocalDateTime date) {
        return date.format(formatter);
    }

}
