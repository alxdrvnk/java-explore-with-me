package ru.practicum.main.controller.publicapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface PublicEventApi {

    @GetMapping("/events")
    //TODO: add class with request Params
    ResponseEntity<Object> getEvents();

    @GetMapping("/events/{id}")
    ResponseEntity<Object> getEventBy(@PathVariable("id") Long id);
}
