package ru.practicum.main.controller.adminapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.category.NewCategoryDto;
import ru.practicum.main.mapper.category.CategoryMapper;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.service.category.CategoryService;

@Slf4j(topic = "Admin Category Controller")
@RestController
@RequiredArgsConstructor
public class AdminCategoryController implements AdminCategoryApi {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<CategoryDto> createCategory(NewCategoryDto newCategoryDto) {
        log.info("Admin request for create Category with data:{}", newCategoryDto);
        Category category = categoryService.createCategory(categoryMapper.toCategory(newCategoryDto));
        return ResponseEntity.ok(categoryMapper.toCategoryDto(category));
    }

    @Override
    public ResponseEntity<CategoryDto> updateCategory(Long id, CategoryDto categoryDto) {
        log.info("Admin update request with data:{} for Category with id: {}", categoryDto, id);
        Category category = categoryService.updateCategory(id, categoryMapper.toCategory(categoryDto));
        return ResponseEntity.ok(categoryMapper.toCategoryDto(category));
    }

    @Override
    public void deleteCategory(Long id) {
        log.info("Admin delete request for Category with id: {}", id);
        categoryService.deleteCategory(id);
    }
}