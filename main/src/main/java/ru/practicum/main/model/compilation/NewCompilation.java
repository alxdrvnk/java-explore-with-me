package ru.practicum.main.model.compilation;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.Collection;

@Value
@Builder
public class NewCompilation {
    @With
    Collection<Long> events;
    Boolean pinned;
    String title;
}
