package ru.practicum.main.model.event;

import lombok.Builder;
import lombok.Value;
import ru.practicum.main.model.request.ParticipationRequest;

import java.util.Collection;

@Value
@Builder
public class EventRequestStatusUpdateResult {
    Collection<ParticipationRequest> confirmedRequests;
    Collection<ParticipationRequest> rejectedRequests;
}
