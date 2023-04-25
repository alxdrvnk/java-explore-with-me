package ru.practicum.main.controller.privateapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.event.EventRequestStatusUpdateRequestDto;
import ru.practicum.main.dto.event.NewEventDto;
import ru.practicum.main.dto.event.UpdateEventUserRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RequestMapping("/users/{userId}/events")
public interface PrivateEventApi {

    @PostMapping
    ResponseEntity<Object> createEvent(@PathVariable Long userId,
                                       @Valid @RequestBody NewEventDto dto);

    @GetMapping
    ResponseEntity<Object> getEventsByUser(@PathVariable("userId") Long userId,
                                           @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(defaultValue = "10") @Positive Integer size);

    @GetMapping("/{eventId}")
    ResponseEntity<Object> getEventById(@PathVariable("userId") Long userId,
                                        @PathVariable("eventId") Long eventId);

    @PatchMapping("/{eventId}")
    ResponseEntity<Object> updateEventById(@PathVariable("userId") Long userId,
                                           @PathVariable("eventId") Long eventId,
                                           @Valid @RequestBody UpdateEventUserRequestDto requestDto);

    @GetMapping("/{eventId}/requests")
    ResponseEntity<Object> getRequests(@PathVariable("userId") Long userId,
                                       @PathVariable("eventId") Long eventId);

    @PatchMapping("/{eventId}/requests")
    ResponseEntity<Object> updateRequests(@PathVariable("userId") Long userId,
                                          @PathVariable("eventId") Long eventId,
                                          @RequestBody EventRequestStatusUpdateRequestDto requestDto);
}
