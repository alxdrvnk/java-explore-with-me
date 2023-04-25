package ru.practicum.main.dto.event;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class EventRequestStatusUpdateRequestDto {
    List<Long> requestIds;
    String status;
}
