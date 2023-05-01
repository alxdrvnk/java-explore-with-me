package ru.practicum.main.mapper.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.main.converter.DateTimeConverter;
import ru.practicum.main.dto.event.EventRequestStatusUpdateResultDto;
import ru.practicum.main.dto.request.ParticipationRequestDto;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.model.event.EventRequestStatusUpdateResult;
import ru.practicum.main.model.request.ParticipationRequest;
import ru.practicum.main.model.request.RequestStatus;
import ru.practicum.main.model.user.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestMapper {

    private final DateTimeConverter converter;

    public ParticipationRequest toParticipationRequest(ParticipationRequestDto participationRequestDto) {
        return ParticipationRequest.builder()
                .event(Event.builder().id(participationRequestDto.getEvent()).build())
                .requester(User.builder().id(participationRequestDto.getRequester()).build())
                .createdDate(converter.parseDate(participationRequestDto.getCreated()))
                .status(RequestStatus.valueOf(participationRequestDto.getStatus()))
                .build();
    }

    public ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return ParticipationRequestDto.builder()
                .id(participationRequest.getId())
                .event(participationRequest.getEvent().getId())
                .requester(participationRequest.getRequester().getId())
                .created(converter.formatDate(participationRequest.getCreatedDate()))
                .status(participationRequest.getStatus().name())
                .build();

    }

    public Collection<ParticipationRequestDto> toParticipationRequestDtoList(Collection<ParticipationRequest> requests) {
        return requests.stream().map(this::toParticipationRequestDto).collect(Collectors.toList());
    }

    public EventRequestStatusUpdateResultDto toEventRequestStatusUpdateResultDto(EventRequestStatusUpdateResult result) {
        return EventRequestStatusUpdateResultDto.builder()
                .confirmedRequests(result.getConfirmedRequests().stream()
                        .map(this::toParticipationRequestDto).collect(Collectors.toList()))
                .rejectedRequests(result.getRejectedRequests().stream()
                        .map(this::toParticipationRequestDto).collect(Collectors.toList()))
                .build();
    }
}

