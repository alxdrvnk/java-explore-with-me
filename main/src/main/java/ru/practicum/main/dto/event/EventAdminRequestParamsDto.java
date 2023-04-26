package ru.practicum.main.dto.event;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EventAdminRequestParamsDto {
    Long[] users;
    String[] states;
    Long[] categories;
    String rangeStart;
    String rangeEnd;
    int from = 0;
    int size = 10;
}
