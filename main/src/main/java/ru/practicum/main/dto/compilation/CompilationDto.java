package ru.practicum.main.dto.compilation;

import lombok.Builder;
import lombok.Value;
import ru.practicum.main.dto.event.EventShortDto;

import java.util.List;

@Value
@Builder
public class CompilationDto {
    Long id;
    List<EventShortDto> events;
    Boolean pinned;
    String title;
}
