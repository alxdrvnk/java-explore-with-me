package ru.practicum.main.controller.privateapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.event.*;
import ru.practicum.main.dto.request.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

public interface PrivateEventApi extends PrivateApi {

    @PostMapping("/events")
    ResponseEntity<EventFullDto> createEvent(@PathVariable Long userId,
                                             @Valid @RequestBody NewEventDto dto);

    @GetMapping("/events")
    ResponseEntity<Collection<EventShortDto>> getEventsByUser(@PathVariable("userId") Long userId,
                                                              @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                              @RequestParam(defaultValue = "10") @Positive Integer size);

    @GetMapping("/events/{eventId}")
    ResponseEntity<EventFullDto> getEventById(@PathVariable("userId") Long userId,
                                              @PathVariable("eventId") Long eventId);

    @PatchMapping("/events/{eventId}")
    ResponseEntity<EventFullDto> updateEventById(@PathVariable("userId") Long userId,
                                                 @PathVariable("eventId") Long eventId,
                                                 @Valid @RequestBody UpdateEventUserRequestDto requestDto);

    @GetMapping("/events/{eventId}/requests")
    ResponseEntity<Collection<ParticipationRequestDto>> getRequests(@PathVariable("userId") Long userId,
                                                                    @PathVariable("eventId") Long eventId);

    @PatchMapping("/events/{eventId}/requests")
    ResponseEntity<EventRequestStatusUpdateResultDto> updateRequests(@PathVariable("userId") Long userId,
                                                                     @PathVariable("eventId") Long eventId,
                                                                     @RequestBody EventRequestStatusUpdateRequestDto requestDto);
}
