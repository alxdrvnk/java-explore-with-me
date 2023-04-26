package ru.practicum.main.controller.adminapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.dto.event.EventFullDto;
import ru.practicum.main.dto.event.EventSearchFilter;
import ru.practicum.main.dto.event.UpdateEvenAdminRequest;
import ru.practicum.main.mapper.event.EventMapper;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.service.event.EventService;

import java.util.Collection;

@Slf4j(topic = "Admin Event Controller")
@RestController
@RequiredArgsConstructor
public class AdminEventController implements AdminEventApi {

    private final EventService eventService;
    private final EventMapper eventMapper;
    @Override
    public ResponseEntity<Collection<EventFullDto>> getAllEvents(EventSearchFilter eventSearchFilter) {
        log.info("Admin get request for all events with filters: {}", eventSearchFilter);
        Collection<Event> events = eventService.getAllEvents(eventSearchFilter);
        return ResponseEntity.ok(eventMapper.toEventFullDtoList(events));
    }

    @Override
    public ResponseEntity<EventFullDto> updateEvent(Long id, UpdateEvenAdminRequest updateRequest) {
        log.info("Admin update event with data: {}", updateRequest);
        return ResponseEntity.ok(eventMapper.toEventFullDto(eventService.updateEventByAdmin(id, updateRequest)));
    }
}
