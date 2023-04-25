package ru.practicum.main.dto.event;

import lombok.Builder;
import lombok.Value;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.user.UserShortDto;

@Value
@Builder
public class EventFullDto {
    Long id;
    String annotation;
    CategoryDto category;
    int confirmedRequests;
    String createdOn;
    String description;
    String eventDate;
    UserShortDto initiator;
    LocationDto location;
    Boolean paid;
    int participantLimit;
    String publishedOn;
    Boolean requestModeration;
    String state;
    String title;
    int views;
}
