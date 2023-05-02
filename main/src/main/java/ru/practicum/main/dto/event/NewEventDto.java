package ru.practicum.main.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import ru.practicum.main.converter.DateTimeConverter;

import javax.validation.constraints.*;

@Value
@Builder
public class NewEventDto {
    @NotBlank
    @Size(min = 20, max = 2000)
    String annotation;
    @NotNull
    Long category;
    @NotBlank
    @Size(min = 20, max = 7000)
    String description;
    @Pattern(regexp = DateTimeConverter.DateTimeRegEx)
    String eventDate;
    @NotNull
    LocationDto location;
    Boolean paid;
    @PositiveOrZero
    Integer participantLimit;
    Boolean requestModeration;
    @NotBlank
    @Size(min = 3, max = 120)
    String title;

    public NewEventDto(@JsonProperty(value = "annotation") String annotation,
                       @JsonProperty(value = "category") Long category,
                       @JsonProperty(value = "description") String description,
                       @JsonProperty(value = "eventDate") String eventDate,
                       @JsonProperty(value = "location") LocationDto location,
                       @JsonProperty(value = "paid") Boolean paid,
                       @JsonProperty(value = "participationLimit") Integer participantLimit,
                       @JsonProperty(value = "requestModeration") Boolean requestModeration,
                       @JsonProperty(value = "title") String title) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.paid = paid;
        if (participantLimit == null || participantLimit < 0) {
            this.participantLimit = 0;
        } else {
            this.participantLimit = participantLimit;
        }
        this.requestModeration = requestModeration;
        this.title = title;
    }
}
