package ru.practicum.main.controller.adminapi;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.event.EventFullDto;
import ru.practicum.main.dto.event.EventSearchFilter;
import ru.practicum.main.dto.event.UpdateEvenAdminRequest;

import javax.validation.Valid;
import java.util.Collection;

public interface AdminEventApi extends AdminApi {
    @GetMapping("/events")
    ResponseEntity<Collection<EventFullDto>> getAllEvents(EventSearchFilter eventSearchFilter);

    @PatchMapping("/events/{eventId}")
    ResponseEntity<EventFullDto> updateEvent(@PathVariable("eventId") Long id,
                                             @RequestBody @Valid UpdateEvenAdminRequest updateRequest);
}
