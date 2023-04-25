package ru.practicum.main.dto.event;

import lombok.Builder;
import lombok.Value;
import ru.practicum.main.dto.request.ParticipationRequestDto;

import java.util.List;

@Value
@Builder
public class EventRequestStatusUpdateResultDto {
    List<ParticipationRequestDto> confirmedRequests;
    List<ParticipationRequestDto> rejectedRequests;
}
