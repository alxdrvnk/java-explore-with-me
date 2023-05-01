package ru.practicum.main.dto.event;

import lombok.Value;

@Value
public class EventSearchFilter {
    String text;
    Long[] users;
    String[] states;
    Long[] categories;
    String rangeStart;
    String rangeEnd;
    Boolean paid;
    String sort;
    boolean onlyAvailable = false;
    int from = 0;
    int size = 10;
}
