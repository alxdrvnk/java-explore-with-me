package ru.practicum.main.controller.publicapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.practicum.main.dto.event.EventFullDto;
import ru.practicum.main.dto.event.EventShortDto;
import ru.practicum.main.dto.event.PublicEventSearchFilter;
import ru.practicum.main.mapper.event.EventMapper;
import ru.practicum.main.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class PublicEventController implements PublicEventApi {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @Override
    public ResponseEntity<Collection<EventShortDto>> getEvents(PublicEventSearchFilter filter,
                                                               HttpServletRequest request) {
        return ResponseEntity.ok(
                eventMapper.toEventShortDtoList(eventService.getAllEvents(
                        eventMapper.toEventSearchFilter(filter),
                        request.getRequestURI(),
                        request.getRemoteAddr())));

    }

    @Override
    public ResponseEntity<EventFullDto> getEventBy(Long id, HttpServletRequest request) {
        return ResponseEntity.ok(
                eventMapper.toEventFullDto(eventService.getEventByIdPublic(
                        id,
                        request.getRequestURI(),
                        request.getRemoteAddr())));
    }
}
