package ru.practicum.main.controller.adminapi;

import org.springframework.http.ResponseEntity;
import ru.practicum.main.dto.compilation.NewCompilationDto;
import ru.practicum.main.dto.compilation.UpdateCompilationRequestDto;

public class AdminCompilationController implements AdminCompilationApi {
    @Override
    public ResponseEntity<Object> createCompilation(NewCompilationDto compilationDto) {
        return null;
    }

    @Override
    public ResponseEntity<Object> deleteCompilation(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Object> updateCompilation(Long id, UpdateCompilationRequestDto dto) {
        return null;
    }
}
