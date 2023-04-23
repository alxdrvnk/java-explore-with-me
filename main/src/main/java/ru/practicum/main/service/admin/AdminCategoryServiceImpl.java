package ru.practicum.main.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.exception.EwmAlreadyExistsException;
import ru.practicum.main.exception.EwmInternalServerException;
import ru.practicum.main.model.Category;
import ru.practicum.main.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Slf4j(topic = "Admin Category Service")
@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

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
    public Category updateCategory(Long id, Category category) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }

    @Override
    public Category getById(Long id) {
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }
}
