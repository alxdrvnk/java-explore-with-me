package ru.practicum.main.controller.adminapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.compilation.NewCompilationDto;
import ru.practicum.main.dto.compilation.UpdateCompilationRequestDto;

import javax.validation.Valid;

public interface AdminCompilationApi extends AdminApi {
    @PostMapping("/compilations")
    ResponseEntity<Object> createCompilation(@RequestBody @Valid NewCompilationDto compilationDto);

    @DeleteMapping("/compilations/{id}")
    ResponseEntity<Object> deleteCompilation(@PathVariable("id") Long id);

    @PatchMapping("/compilations/{id}")
    ResponseEntity<Object> updateCompilation(@PathVariable("id") Long id,
                                             @RequestBody UpdateCompilationRequestDto dto);
}
