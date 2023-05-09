package ru.practicum.main.dto.compilation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Value
@Builder
public class NewCompilationDto {
    List<Long> events;
    boolean pinned;
    @NotBlank
    @Size(max = 255)
    String title;

    @JsonCreator
    public NewCompilationDto(@JsonProperty(value = "events") List<Long> events,
                             @JsonProperty(value = "pinned") Boolean pinned,
                             @JsonProperty(value = "title", required = true) String title) {
        this.events = events;
        this.pinned = pinned;
        this.title = title;
    }
}
