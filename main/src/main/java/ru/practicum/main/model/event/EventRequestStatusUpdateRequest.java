package ru.practicum.main.model.event;

import lombok.Builder;
import lombok.Value;
import ru.practicum.main.model.request.RequestStatus;

import java.util.List;

@Value
@Builder
public class EventRequestStatusUpdateRequest {
    List<Long> requestIds;
    RequestStatus status;
}
