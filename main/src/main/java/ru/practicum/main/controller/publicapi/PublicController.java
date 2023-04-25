package ru.practicum.main.controller.publicapi;

import ru.practicum.main.dto.category.CategoryDto;

import java.util.List;

public class PublicController implements PublicApi{

    @Override
    public List<CategoryDto> getAllCategories(Integer from, Integer size) {
        return null;
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return null;
    }
}
