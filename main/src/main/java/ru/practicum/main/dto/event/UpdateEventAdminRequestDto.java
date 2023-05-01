package ru.practicum.main.dto.event;

import lombok.Builder;
import lombok.Value;
import ru.practicum.main.converter.DateTimeConverter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
@Builder
public class UpdateEventAdminRequestDto {
    @Size(min = 20, max = 2000)
    String annotation;
    Long categoryId;
    @Size(min = 20, max = 7000)
    String description;
    @Pattern(regexp = DateTimeConverter.DateTimeRegEx)
    String eventDate;
    LocationDto location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    StateAction stateAction;
    @Size(min = 3, max = 120)
    String title;
}
