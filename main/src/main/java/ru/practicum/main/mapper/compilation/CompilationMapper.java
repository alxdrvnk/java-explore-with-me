package ru.practicum.main.mapper.compilation;

import org.springframework.stereotype.Component;
import ru.practicum.main.dto.compilation.CompilationDto;
import ru.practicum.main.dto.compilation.NewCompilationDto;
import ru.practicum.main.dto.compilation.UpdateCompilationRequestDto;
import ru.practicum.main.model.compilation.Compilation;
import ru.practicum.main.model.event.Event;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CompilationMapper {
    public Compilation toCompilation(NewCompilationDto compilationDto) {
        return Compilation.builder()
                .pinned(compilationDto.getPinned())
                .title(compilationDto.getTitle())
                .build();
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
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

        if (update.getTitle() != null) {
            updatedCompilation = updatedCompilation.withTitle(update.getTitle());
        }

        return updatedCompilation;
    }
}