package ru.practicum.main.controller.adminapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.category.NewCategoryDto;

public interface AdminCategoryApi {
    @PostMapping("/categories")
    ResponseEntity<CategoryDto> createCategory(@RequestBody NewCategoryDto newCategoryDto);

    @DeleteMapping("/categories/{id}")
    void deleteCategory(@PathVariable("id") Long id);

    @PatchMapping("/categories/{id}")
    CategoryDto updateCategory(@PathVariable("id") Long id,
                               @RequestBody CategoryDto categoryDto);
}
