package ru.practicum.main.dto.event;

public class EventSearchFilter {
    String text;
    Long[] users;
    String[] states;
    Long[] categories;
    String rangeStart;
    String rangeEnd;
    boolean onlyAvailable = false;
    int from = 0;
    int size = 10;
}
