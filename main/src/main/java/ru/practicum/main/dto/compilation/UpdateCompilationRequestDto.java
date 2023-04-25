package ru.practicum.main.dto.compilation;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UpdateCompilationRequestDto {
    List<Long> events;
    Boolean pinned;
    String title;
}
