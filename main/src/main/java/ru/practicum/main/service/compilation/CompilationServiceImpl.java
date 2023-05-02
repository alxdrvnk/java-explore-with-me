package ru.practicum.main.service.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main.exception.EwmNotFoundException;
import ru.practicum.main.mapper.compilation.CompilationMapper;
import ru.practicum.main.model.compilation.Compilation;
import ru.practicum.main.repository.CompilationRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Slf4j(topic = "Compilation Service")
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    @Transactional
    public Compilation createCompilation(Compilation compilation) {
        log.info("Create new Compilation: {}", compilation);
        return compilationRepository.save(compilation);
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
        try {
            compilationRepository.deleteById(id);
        } catch (Exception e) {
            throw new EwmNotFoundException(String.format("Compilation with id: %d not found", id));
        }
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
