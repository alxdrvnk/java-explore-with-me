package ru.practicum.main.controller.adminapi;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.event.EventFullDto;
import ru.practicum.main.dto.event.EventSearchFilter;
import ru.practicum.main.dto.event.UpdateEventAdminRequestDto;

import javax.validation.Valid;
import java.util.Collection;

public interface AdminEventApi extends AdminApi {
    @GetMapping("/events")
    ResponseEntity<Collection<EventFullDto>> getAllEvents(EventSearchFilter eventSearchFilter);

    @PatchMapping("/events/{id}")
    ResponseEntity<EventFullDto> updateEvent(@PathVariable("id") Long id,
                                             @RequestBody @Valid UpdateEventAdminRequestDto updateRequest);
}
