package ru.practicum.main.dto.compilation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UpdateCompilationRequestDto {
    List<Long> events;
    Boolean pinned;
    String title;

    @JsonCreator
    public UpdateCompilationRequestDto(@JsonProperty(value = "events") List<Long> events,
                                       @JsonProperty(value = "pinned") Boolean pinned,
                                       @JsonProperty(value = "title") String title)  {
        this.events = events;
        this.pinned = pinned;
        this.title = title;
    }
}
