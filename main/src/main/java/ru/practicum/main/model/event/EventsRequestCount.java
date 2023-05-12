package ru.practicum.main.model.event;

import lombok.Value;

@Value
public class EventsRequestCount {
    Long eventId;
    Integer reqCount;
}
