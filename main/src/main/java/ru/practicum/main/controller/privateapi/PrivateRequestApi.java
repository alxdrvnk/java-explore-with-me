package ru.practicum.main.controller.privateapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users/{userId}")
public interface PrivateRequestApi {

    @GetMapping("/requests")
    ResponseEntity<Object> createEvent(@PathVariable("userId") Long userId);

    @PostMapping("/requests")
    ResponseEntity<Object> createRequest(@PathVariable("userId") Long userId,
                                         @RequestParam Long eventId);

    @PatchMapping("/requests/{requestId}/cancel")
    ResponseEntity<Object> cancelRequest(@PathVariable("userId") Long userId,
                                         @PathVariable("requestId") Long requestId);
}
