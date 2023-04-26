package ru.practicum.main.dto.event;

public class EventSearchFilter {
    Long[] users;
    String[] states;
    Long[] categories;
    String rangeStart;
    String rangeEnd;
    int from = 0;
    int size = 10;
}
