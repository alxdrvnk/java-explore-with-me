package ru.practicum.stats.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ViewStatsDto {
    String app;
    String uri;
    Long hits;
}
