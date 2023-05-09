package ru.practicum.main.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime eventDate;
    @Valid
    LocationDto location;
    boolean paid;
    int participantLimit;
    boolean requestModeration;
    @NotBlank
    @Size(min = 3, max = 120)
    String title;

    @JsonCreator
    public NewEventDto(@JsonProperty(value = "annotation") String annotation,
                       @JsonProperty(value = "category") Long category,
                       @JsonProperty(value = "description") String description,
                       @JsonProperty(value = "eventDate") LocalDateTime eventDate,
                       @JsonProperty(value = "location") LocationDto location,
                       @JsonProperty(value = "paid") Boolean paid,
                       @JsonProperty(value = "participantLimit") Integer participantLimit,
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
        this.requestModeration = requestModeration == null || requestModeration;
        this.title = title;
    }
}
