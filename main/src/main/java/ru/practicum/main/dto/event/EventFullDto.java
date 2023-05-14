package ru.practicum.main.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.user.UserShortDto;

@Getter
@Builder
public class EventFullDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private UserShortDto initiator;
    private LocationDto location;
    private Boolean paid;
    private int participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private String state;
    private String title;
    private int views;

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
