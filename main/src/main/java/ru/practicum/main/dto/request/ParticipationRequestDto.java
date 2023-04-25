package ru.practicum.main.dto.request;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ParticipationRequestDto {
    Long id;
    Long event;
    Long requester;
    String created;
    String status;
}
