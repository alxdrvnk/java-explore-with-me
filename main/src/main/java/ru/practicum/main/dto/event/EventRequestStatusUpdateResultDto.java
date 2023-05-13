package ru.practicum.main.dto.event;

import lombok.Builder;
import lombok.Value;
import ru.practicum.main.dto.request.ParticipationRequestDto;

import java.util.Collection;

@Value
@Builder
public class EventRequestStatusUpdateResultDto {
    Collection<ParticipationRequestDto> confirmedRequests;
    Collection<ParticipationRequestDto> rejectedRequests;
}
