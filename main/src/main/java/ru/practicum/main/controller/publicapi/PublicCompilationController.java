package ru.practicum.main.controller.publicapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.dto.compilation.CompilationDto;
import ru.practicum.main.mapper.compilation.CompilationMapper;
import ru.practicum.main.service.compilation.CompilationService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class PublicCompilationController implements PublicCompilationApi {

    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @Override
    public ResponseEntity<Collection<CompilationDto>> getCompilations(Boolean pinned, Integer from, Integer size) {
        return ResponseEntity.ok(
                compilationMapper.toCompilationDtoList(compilationService.getAllCompilation(pinned, from, size)));
    }

    @Override
    public ResponseEntity<CompilationDto> getCompilationById(Long id) {
        return ResponseEntity.ok(
                compilationMapper.toCompilationDto(compilationService.getCompilationBy(id)));
    }
}
