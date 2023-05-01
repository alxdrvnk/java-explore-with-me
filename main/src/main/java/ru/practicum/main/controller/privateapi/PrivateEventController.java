package ru.practicum.main.controller.privateapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.dto.event.*;
import ru.practicum.main.dto.request.ParticipationRequestDto;
import ru.practicum.main.mapper.event.EventMapper;
import ru.practicum.main.mapper.request.RequestMapper;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.model.event.EventRequestStatusUpdateResult;
import ru.practicum.main.model.request.ParticipationRequest;
import ru.practicum.main.service.event.EventService;

import java.util.Collection;

@Slf4j(topic = "Private Event Controller")
@RestController
@RequiredArgsConstructor
public class PrivateEventController implements PrivateEventApi {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final RequestMapper requestMapper;

    @Override
    public ResponseEntity<EventFullDto> createEvent(Long userId, NewEventDto dto) {
        log.info("User with id: {} create Event with data: {}", userId, dto);
        Event event = eventService.createEvent(userId, eventMapper.toNewEventRequest(dto));
        return new ResponseEntity<>(eventMapper.toEventFullDto(event), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Collection<EventShortDto>> getEventsByUser(Long userId, Integer from, Integer size) {
        Collection<Event> events = eventService.getEventByUser(userId, from, size);
        return ResponseEntity.ok(eventMapper.toEventShortDtoList(events));
    }

    @Override
    public ResponseEntity<EventFullDto> getEventById(Long userId, Long eventId) {
        Event event = eventService.getEventByIdPrivate(userId, eventId);
        return ResponseEntity.ok(eventMapper.toEventFullDto(event));
    }

    @Override
    public ResponseEntity<EventFullDto> updateEventById(Long userId, Long eventId, UpdateEventUserRequestDto requestDto) {
        log.info("User with id: {} update Event with id: {} with data: {}", userId, eventId, requestDto);
        Event event = eventService.updateEventById(userId, eventId, eventMapper.toUpdateEventRequest(requestDto));
        return ResponseEntity.ok(eventMapper.toEventFullDto(event));
    }

    @Override
    public ResponseEntity<Collection<ParticipationRequestDto>> getRequests(Long userId, Long eventId) {
        Collection<ParticipationRequest> requests = eventService.getEventRequests(userId, eventId);
        return ResponseEntity.ok(requestMapper.toParticipationRequestDtoList(requests));
    }

    @Override
    public ResponseEntity<EventRequestStatusUpdateResultDto> updateRequests(Long userId,
                                                                            Long eventId,
                                                                            EventRequestStatusUpdateRequestDto requestDto) {
        log.info("User with id: {} update request for Event with id {} with data: {}", userId, eventId, requestDto);
        EventRequestStatusUpdateResult result =
                eventService.updateEventRequests(
                        userId,
                        eventId,
                        eventMapper.toEventRequestStatusUpdateRequests(requestDto));
        return ResponseEntity.ok(requestMapper.toEventRequestStatusUpdateResultDto(result));
    }
}
