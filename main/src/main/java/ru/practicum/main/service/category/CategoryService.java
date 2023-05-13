package ru.practicum.main.service.category;

import ru.practicum.main.model.category.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);

    Category getById(Long id);

    List<Category> getAllCategories(Integer from, Integer size);
}
