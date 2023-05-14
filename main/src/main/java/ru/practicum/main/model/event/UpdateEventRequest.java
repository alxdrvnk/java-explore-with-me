package ru.practicum.main.model.event;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import ru.practicum.main.model.comment.AdminEventComment;

import java.time.LocalDateTime;

@Value
@Builder
@ToString
public class UpdateEventRequest {
    String annotation;
    Long categoryId;
    String description;
    LocalDateTime eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    EventState eventState;
    String title;
    AdminEventComment comment;
}
