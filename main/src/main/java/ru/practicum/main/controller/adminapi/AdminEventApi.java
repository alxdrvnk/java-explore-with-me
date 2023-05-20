package ru.practicum.main.controller.adminapi;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.event.EventFullWithCommentsDto;
import ru.practicum.main.dto.event.EventSearchFilter;
import ru.practicum.main.dto.event.UpdateEventAdminRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

public interface AdminEventApi extends AdminApi {
    @GetMapping("/events")
    ResponseEntity<Collection<EventFullWithCommentsDto>> getAllEvents(@Valid EventSearchFilter eventSearchFilter);

    @PatchMapping("/events/{id}")
    ResponseEntity<EventFullWithCommentsDto> updateEvent(@PathVariable("id") Long id,
                                                         @RequestBody @Valid UpdateEventAdminRequestDto updateRequest);

    @GetMapping("/events/pending")
    ResponseEntity<Collection<EventFullWithCommentsDto>> getPendingEvents(@RequestParam(defaultValue = "0")
                                                                          @PositiveOrZero Integer from,
                                                                          @RequestParam(defaultValue = "10")
                                                                          @Positive Integer size);
}
