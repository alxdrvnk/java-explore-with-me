package ru.practicum.main.dto.compilation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import ru.practicum.main.dto.event.EventShortDto;

import java.util.Collection;

@Value
@Builder
public class CompilationDto {
    Long id;
    Collection<EventShortDto> events;
    Boolean pinned;
    String title;

    @JsonCreator
    public CompilationDto(@JsonProperty(value = "id", required = true) Long id,
                          @JsonProperty(value = "events") Collection<EventShortDto> events,
                          @JsonProperty(value = "pinned", required = true) Boolean pinned,
                          @JsonProperty(value = "title",required = true) String title) {
        this.id = id;
        this.events = events;
        this.pinned = pinned;
        this.title = title;
    }
}
