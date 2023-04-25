package ru.practicum.main.dto.event;

import lombok.Builder;
import lombok.Value;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.user.UserShortDto;

@Value
@Builder
public class EventShortDto {
    Long id;
    String annotation;
    CategoryDto category;
    int confirmedRequests;
    String eventDate;
    UserShortDto initiator;
    Boolean paid;
    String title;
    int views;
}
