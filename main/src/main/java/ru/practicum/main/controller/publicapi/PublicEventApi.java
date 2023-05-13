package ru.practicum.main.controller.publicapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.practicum.main.dto.event.EventFullDto;
import ru.practicum.main.dto.event.EventShortDto;
import ru.practicum.main.dto.event.PublicEventSearchFilter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;

@RequestMapping("/events")
public interface PublicEventApi {

    @GetMapping
    ResponseEntity<Collection<EventShortDto>> getEvents(@Valid PublicEventSearchFilter filter,
                                                        HttpServletRequest request);

    @GetMapping("/{id}")
    ResponseEntity<EventFullDto> getEventBy(@PathVariable("id") Long id, HttpServletRequest request);
}
