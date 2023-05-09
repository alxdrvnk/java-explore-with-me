package ru.practicum.main.service.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main.exception.EwmNotFoundException;
import ru.practicum.main.mapper.compilation.CompilationMapper;
import ru.practicum.main.model.compilation.Compilation;
import ru.practicum.main.model.compilation.NewCompilation;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.repository.CompilationRepository;
import ru.practicum.main.service.event.EventService;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Slf4j(topic = "Compilation Service")
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventService eventService;
    private final CompilationMapper compilationMapper;

    @Override
    @Transactional
    public Compilation createCompilation(NewCompilation compilation) {
        log.info("Create new Compilation: {}", compilation);
        List<Event> events = eventService.getAllEventsByIds(compilation.getEvents());
        if (events == null) {
            events = Collections.emptyList();
        }
        return compilationRepository.save(
                new Compilation(null,
                        compilation.getPinned(),
                        compilation.getTitle(),
                        new HashSet<>(events)));

    }

    @Override
    @Transactional
    public Compilation updateCompilation(Long id, Compilation update) {
        log.info("Update Compilation with id: {}. Data: {}", id, update);
        Compilation dbCompilation = getCompilationBy(id);
        return compilationRepository.save(compilationMapper.partialUpdateCompilation(dbCompilation, update));
    }

    @Override
    @Transactional
    public void deleteCompilation(Long id) {
        log.info("Delete Compilation with id: {}", id);
        if (!compilationRepository.existsById(id)) {
            throw new EwmNotFoundException(String.format("Compilation with id: %d not found", id));
        }
        compilationRepository.deleteById(id);
    }

    @Override
    public Collection<Compilation> getAllCompilation(Boolean pinned, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        if (pinned != null) {
            return compilationRepository.findAllByPinnedIs(pinned, pageRequest);
        } else {
            return compilationRepository.findAll(pageRequest).getContent();
        }
    }

    @Override
    public Compilation getCompilationBy(Long id) {
        return compilationRepository.findById(id).orElseThrow(
                () -> new EwmNotFoundException(String.format("Compilation with Id %d not found", id)));
    }
}
