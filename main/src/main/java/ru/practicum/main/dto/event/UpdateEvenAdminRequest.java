package ru.practicum.main.dto.event;

import lombok.Builder;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@Valid
@Builder
public class UpdateEvenAdminRequest {
    @Size(min = 20, max = 2000)
    String annotation;
    Long category;
    @Size(min = 20, max = 7000)
    String description;
    String eventDate;
    LocationDto location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    StateAction stateAction;
    @Size(min = 3, max = 120)
    String title;
}
