package ru.practicum.main.service.admin;

import ru.practicum.main.model.Category;

import java.util.List;

public interface AdminCategoryService {

    Category createCategory(Category category);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);

    Category getById(Long id);

    List<Category> getAllCategories();
}
