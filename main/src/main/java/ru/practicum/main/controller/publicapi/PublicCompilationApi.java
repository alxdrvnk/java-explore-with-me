package ru.practicum.main.controller.publicapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface PublicCompilationApi {
    @GetMapping("/compilations")
    ResponseEntity<Object> getCompilations(@RequestParam(required = false) Boolean pinned,
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size);
    @GetMapping("/compilations/{id}")
    ResponseEntity<Object> getCompilationById(@PathVariable("ic") Long id);
}
