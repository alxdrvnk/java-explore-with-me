package ru.practicum.main.mapper.category;

import org.springframework.stereotype.Component;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.category.NewCategoryDto;
import ru.practicum.main.model.category.Category;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    public CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category toCategory(NewCategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    public Category toCategory(CategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    public List<CategoryDto> toCategoryDtoList(List<Category> categoryList) {
        return categoryList.stream().map(this::toCategoryDto).collect(Collectors.toList());
    }
}
