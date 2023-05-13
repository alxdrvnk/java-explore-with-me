package ru.practicum.main.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonCreator
    public EventFullDto(@JsonProperty(value = "id") Long id,
                        @JsonProperty(value = "annotation", required = true) String annotation,
                        @JsonProperty(value = "category", required = true) CategoryDto category,
                        @JsonProperty(value = "confirmedRequests") Integer confirmedRequests,
                        @JsonProperty(value = "createdOn") String createdOn,
                        @JsonProperty(value = "description") String description,
                        @JsonProperty(value = "eventDate", required = true) String eventDate,
                        @JsonProperty(value = "initiator", required = true) UserShortDto initiator,
                        @JsonProperty(value = "location", required = true) LocationDto location,
                        @JsonProperty(value = "paid", required = true) Boolean paid,
                        @JsonProperty(value = "participantLimit") Integer participantLimit,
                        @JsonProperty(value = "publishedOn") String publishedOn,
                        @JsonProperty(value = "requestModeration") Boolean requestModeration,
                        @JsonProperty(value = "state") String state,
                        @JsonProperty(value = "title", required = true) String title,
                        @JsonProperty(value = "views") Integer views) {
        this.id = id;
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.createdOn = createdOn;
        this.description = description;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        this.requestModeration = requestModeration;
        this.state = state;
        this.title = title;
        this.views = views;
    }
}
