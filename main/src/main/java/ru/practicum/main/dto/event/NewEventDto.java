package ru.practicum.main.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import ru.practicum.main.converter.DateTimeConverter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
        this.participantLimit = participantLimit;
        this.requestModeration = requestModeration;
        this.title = title;
    }
}
