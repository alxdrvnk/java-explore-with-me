package ru.practicum.main.service.compilation;

import ru.practicum.main.model.compilation.Compilation;
import ru.practicum.main.model.compilation.NewCompilation;

import java.util.Collection;

public interface CompilationService {

    Compilation createCompilation(NewCompilation compilation);

    Compilation updateCompilation(Long id, Compilation update);

    void deleteCompilation(Long id);

    Collection<Compilation> getAllCompilation(Boolean pinned, Integer from, Integer size);

    Compilation getCompilationBy(Long id);
}
