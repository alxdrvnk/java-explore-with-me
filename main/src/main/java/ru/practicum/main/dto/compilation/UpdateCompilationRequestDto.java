package ru.practicum.main.dto.compilation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Size;
import java.util.List;

@Value
@Builder
public class UpdateCompilationRequestDto {
    List<Long> events;
    Boolean pinned;
    @Size(max = 255)
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
