package ru.practicum.main.service.compilation;

import ru.practicum.main.model.compilation.Compilation;

import java.util.Collection;

public interface CompilationService {

    Compilation createCompilation(Compilation compilation);

    Compilation updateCompilation(Long id, Compilation compilation);

    void deleteCompilation(Long id);

    Collection<Compilation> getAllCompilation(Boolean pinned, Integer from, Integer size);

    Compilation getCompilationBy(Long id);
}
