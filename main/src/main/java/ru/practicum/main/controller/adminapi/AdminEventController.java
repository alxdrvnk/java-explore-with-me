package ru.practicum.main.controller.adminapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.practicum.main.dto.event.EventFullWithCommentsDto;
import ru.practicum.main.dto.event.EventSearchFilter;
import ru.practicum.main.dto.event.UpdateEventAdminRequestDto;
import ru.practicum.main.mapper.event.EventMapper;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.service.event.EventService;

import java.util.Collection;

@Slf4j(topic = "Admin Event Controller")
@Controller
@RequiredArgsConstructor
public class AdminEventController implements AdminEventApi {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @Override
    public ResponseEntity<Collection<EventFullWithCommentsDto>> getAllEvents(EventSearchFilter eventSearchFilter) {
        log.info("Get events with filter: {}", eventSearchFilter);
        Collection<Event> events = eventService.getAllEvents(eventSearchFilter);
        return ResponseEntity.ok(
                eventMapper.toEventFullWithCommentsDtoList(events));
    }

    @Override
    public ResponseEntity<EventFullWithCommentsDto> updateEvent(Long id, UpdateEventAdminRequestDto updateRequest) {
        log.info("Admin update Event with id: {} with data: {}", id, updateRequest.toString());
        return ResponseEntity.ok(
                eventMapper.toEventFullWithCommentsDto(eventService.updateEventByAdmin(
                        id, eventMapper.toUpdateEventRequest(updateRequest))));
    }

    @Override
    public ResponseEntity<Collection<EventFullWithCommentsDto>> getPendingEvents(Integer from, Integer size) {
        return ResponseEntity.ok(
                eventMapper.toEventFullWithCommentsDtoList(eventService.getAllEventsWithPendingState()));
    }
}
