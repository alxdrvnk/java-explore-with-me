package ru.practicum.main.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.category.NewCategoryDto;
import ru.practicum.main.model.Category;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    Category toCategory(NewCategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    List<CategoryDto> toCategoryDtoList(List<Category> categoryList) {
        return categoryList.stream().map(this::toCategoryDto).collect(Collectors.toList());
    }
}
