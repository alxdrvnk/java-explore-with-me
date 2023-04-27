package ru.practicum.main.controller.adminapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.dto.compilation.CompilationDto;
import ru.practicum.main.dto.compilation.NewCompilationDto;
import ru.practicum.main.dto.compilation.UpdateCompilationRequestDto;
import ru.practicum.main.mapper.compilation.CompilationMapper;
import ru.practicum.main.model.compilation.Compilation;
import ru.practicum.main.service.compilation.CompilationService;

@Slf4j(topic = "Admin Compilation Controller")
@RestController
@RequiredArgsConstructor
public class AdminCompilationController implements AdminCompilationApi {

    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @Override
    public ResponseEntity<CompilationDto> createCompilation(NewCompilationDto compilationDto) {
        log.info("Admin request for create Compilation with data: {}", compilationDto);
        Compilation compilation = compilationService.createCompilation(compilationMapper.toCompilation(compilationDto));
        return ResponseEntity.ok(compilationMapper.toCompilationDto(compilation));
    }

    @Override
    public ResponseEntity<Object> deleteCompilation(Long id) {
        log.info("Admin delete request for Compilation with id: {}", id);
        compilationService.deleteCompilation(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CompilationDto> updateCompilation(Long id, UpdateCompilationRequestDto dto) {
        log.info("Admin update request with data:{} for Compilation with id: {}", dto, id);
        Compilation compilation = compilationService.updateCompilation(id, compilationMapper.toCompilation(dto));
        return ResponseEntity.ok(compilationMapper.toCompilationDto(compilation));
    }
}
