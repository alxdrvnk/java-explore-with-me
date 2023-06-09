package ru.practicum.main.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.user.UserShortDto;

@Getter
@Builder
public class EventShortDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private String eventDate;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Integer views;

    @JsonCreator
    public EventShortDto(@JsonProperty(value = "id") Long id,
                         @JsonProperty(value = "annotation", required = true) String annotation,
                         @JsonProperty(value = "category", required = true) CategoryDto category,
                         @JsonProperty(value = "confirmedRequests") Integer confirmedRequests,
                         @JsonProperty(value = "eventDate", required = true) String eventDate,
                         @JsonProperty(value = "initiator", required = true) UserShortDto initiator,
                         @JsonProperty(value = "paid", required = true) Boolean paid,
                         @JsonProperty(value = "title", required = true) String title,
                         @JsonProperty(value = "views") Integer views) {
        this.id = id;
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = confirmedRequests;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.paid = paid;
        this.title = title;
        this.views = views;
    }
}
