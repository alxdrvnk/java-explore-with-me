package ru.practicum.main.controller.publicapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.mapper.category.CategoryMapper;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.service.category.CategoryService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class PublicCategoryController implements PublicCategoryApi {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<Collection<CategoryDto>> getAllCategories(Integer from, Integer size) {
        Collection<Category> categories = categoryService.getAllCategories(from, size);
        return ResponseEntity.ok(
                categoryMapper.toCategoryDtoList(categories));
    }

    @Override
    public ResponseEntity<CategoryDto> getCategoryById(Long id) {
        return ResponseEntity.ok(
                categoryMapper.toCategoryDto(categoryService.getById(id)));
    }
}
