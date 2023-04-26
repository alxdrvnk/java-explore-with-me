package ru.practicum.main.controller.adminapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.dto.event.EventAdminRequestParamsDto;
import ru.practicum.main.dto.event.EventFullDto;
import ru.practicum.main.dto.event.UpdateEvenAdminRequest;
import ru.practicum.main.service.event.EventService;

import java.util.Collection;

@Slf4j(topic = "Admin Event Controller")
@RestController
@RequiredArgsConstructor
public class AdminEventController implements AdminEventApi {

    private final EventService eventService;

    @Override
    public ResponseEntity<Collection<EventFullDto>> getAllEvents(EventAdminRequestParamsDto requestParamsDto) {
        log.info("Admin get request for all events with params: {}", requestParamsDto);
        return eventService;
    }

    @Override
    public ResponseEntity<EventFullDto> updateEvent(Long id, UpdateEvenAdminRequest updateRequest) {
        return null;
    }
}
