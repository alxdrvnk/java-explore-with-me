package ru.practicum.main.controller.privateapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.request.ParticipationRequestDto;

import java.util.Collection;

public interface PrivateRequestApi extends PrivateApi {

    @GetMapping("/requests")
    ResponseEntity<Collection<ParticipationRequestDto>> getRequestsByUser(@PathVariable("userId") Long userId);

    @PostMapping("/requests")
    ResponseEntity<ParticipationRequestDto> createRequest(@PathVariable("userId") Long userId,
                                                          @RequestParam Long eventId);

    @PatchMapping("/requests/{requestId}/cancel")
    ResponseEntity<ParticipationRequestDto> cancelRequest(@PathVariable("userId") Long userId,
                                                          @PathVariable("requestId") Long requestId);
}
