package ru.practicum.main.controller.publicapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.main.dto.compilation.CompilationDto;

import java.util.Collection;

@RequestMapping("/compilations")
public interface PublicCompilationApi {
    @GetMapping
    ResponseEntity<Collection<CompilationDto>> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                               @RequestParam(defaultValue = "0") Integer from,
                                                               @RequestParam(defaultValue = "10") Integer size);

    @GetMapping("/{id}")
    ResponseEntity<CompilationDto> getCompilationById(@PathVariable("id") Long id);
}
