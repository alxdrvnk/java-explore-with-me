package ru.practicum.main.controller.adminapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.practicum.main.dto.compilation.CompilationDto;
import ru.practicum.main.dto.compilation.NewCompilationDto;
import ru.practicum.main.dto.compilation.UpdateCompilationRequestDto;
import ru.practicum.main.mapper.compilation.CompilationMapper;
import ru.practicum.main.model.compilation.Compilation;
import ru.practicum.main.service.compilation.CompilationService;

@Slf4j(topic = "Admin Compilation Controller")
@Controller
@RequiredArgsConstructor
public class AdminCompilationController implements AdminCompilationApi {

    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @Override
    public ResponseEntity<CompilationDto> createCompilation(NewCompilationDto compilationDto) {
        log.info("Admin request for create Compilation with data: {}", compilationDto);
        Compilation compilation = compilationService.createCompilation(
                compilationMapper.toNewCompilation(compilationDto));
        return new ResponseEntity<>(compilationMapper.toCompilationDto(compilation), HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<Void> deleteCompilation(Long id) {
        log.info("Admin delete request for Compilation with id: {}", id);
        compilationService.deleteCompilation(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CompilationDto> updateCompilation(Long id, UpdateCompilationRequestDto dto) {
        log.info("Admin update request with data:{} for Compilation with id: {}", dto, id);
        Compilation compilation = compilationService.updateCompilation(id, compilationMapper.toCompilation(dto));
        return ResponseEntity.ok(compilationMapper.toCompilationDto(compilation));
    }
}
