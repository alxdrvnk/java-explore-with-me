package ru.practicum.main.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.exception.EwmAlreadyExistsException;
import ru.practicum.main.exception.EwmIllegalArgumentException;
import ru.practicum.main.exception.EwmNotFoundException;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.repository.CategoryRepository;

import java.util.List;

@Slf4j(topic = "Admin Category Service")
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category createCategory(Category category) {
        log.info("Create new category with data: {}", category);
        try {
            return categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new EwmAlreadyExistsException(String.format(
                    "Category with name %s already exists", category.getName()));
        }
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, Category category) {
        Category dbCategory = getById(id);
        if (!dbCategory.getName().equals(category.getName()) && categoryRepository.existsByName(category.getName())) {
            throw new EwmAlreadyExistsException(
                    String.format("Category with Name %s already exists", category.getName()));
        }
        return categoryRepository.save(dbCategory.withName(category.getName()));
    }

    @Override
    public void deleteCategory(Long id) {
        try {
            if (!categoryRepository.existsById(id)) {
                throw new EwmNotFoundException(
                        String.format("Category with Id %d not found", id));
            }
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EwmIllegalArgumentException(
                    String.format("Catgory with id: %d not empty", id));
        }
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new EwmNotFoundException(
                        String.format("Category with Id %d not found", id))
        );
    }

    @Override
    public List<Category> getAllCategories(Integer from, Integer size) {
        return categoryRepository.findAll(PageRequest.of(from / size, size)).getContent();
    }
}
