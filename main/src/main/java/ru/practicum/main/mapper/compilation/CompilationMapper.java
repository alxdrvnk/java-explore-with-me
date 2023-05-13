package ru.practicum.main.mapper.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.main.dto.compilation.CompilationDto;
import ru.practicum.main.dto.compilation.NewCompilationDto;
import ru.practicum.main.dto.compilation.UpdateCompilationRequestDto;
import ru.practicum.main.mapper.event.EventMapper;
import ru.practicum.main.model.compilation.Compilation;
import ru.practicum.main.model.compilation.NewCompilation;
import ru.practicum.main.model.event.Event;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {

    private final EventMapper eventMapper;

    public NewCompilation toNewCompilation(NewCompilationDto compilationDto) {
        return NewCompilation.builder()
                .events(compilationDto.getEvents())
                .pinned(compilationDto.isPinned())
                .title(compilationDto.getTitle())
                .build();
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .events(compilation.getEvents() == null ?
                        Collections.emptyList() :
                        eventMapper.toEventShortDtoList(compilation.getEvents()))
                .title(compilation.getTitle())
                .build();
    }

    public Compilation toCompilation(UpdateCompilationRequestDto updateRequest) {
        return Compilation.builder()
                .events(
                        updateRequest.getEvents().stream()
                                .map(i -> Event.builder().id(i).build()).collect(Collectors.toSet()))
                .pinned(updateRequest.getPinned())
                .title(updateRequest.getTitle())
                .build();
    }

    public Collection<CompilationDto> toCompilationDtoList(Collection<Compilation> allCompilation) {
        return allCompilation.stream().map(this::toCompilationDto).collect(Collectors.toList());
    }

    public Compilation partialUpdateCompilation(Compilation compilation, Compilation update) {
        Compilation updatedCompilation = compilation;

        if (update.getEvents() != null && !update.getEvents().isEmpty()) {
            updatedCompilation = updatedCompilation.withEvents(update.getEvents());
        }

        if (update.getPinned() != null) {
            updatedCompilation =  updatedCompilation.withPinned(update.getPinned());
        }

        if (update.getTitle() != null && !update.getTitle().isBlank()) {
            updatedCompilation = updatedCompilation.withTitle(update.getTitle());
        }

        return updatedCompilation;
    }
}