package ru.practicum.main.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.exception.EwmAlreadyExistsException;
import ru.practicum.main.exception.EwmInternalServerException;
import ru.practicum.main.exception.EwmNotFoundException;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Slf4j(topic = "Admin Category Service")
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category createCategory(Category category) {
        log.info("Create new category with data: {}", category);
        Optional<Category> createdCategory;
        try {
            createdCategory = categoryRepository.findByName(category.getName());
        } catch (Exception e) {
            log.error("Failed to create Category: {}", category, e);
            throw new EwmInternalServerException(String.format(
                    "Failed to create Category %s", category.getName()), e);
        }

        if (createdCategory.isPresent()) {
            log.info("Category {} already exists", category.getName());
            throw new EwmAlreadyExistsException(String.format(
                    "Category %s already exists", category.getName()));
        }

        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            log.error("Failed to create Category {}", category, e);
            throw new EwmInternalServerException(String.format(
                    "Failed to create Category %s", category.getName()), e);
        }
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, Category category) {
        Category dbCategory = getById(id);
        if (categoryRepository.existsByName(category.getName())) {
            throw new EwmAlreadyExistsException(
                    String.format("Category with Name %s already exists", category.getName()));
        }
        return categoryRepository.save(dbCategory.withName(category.getName()));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
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
