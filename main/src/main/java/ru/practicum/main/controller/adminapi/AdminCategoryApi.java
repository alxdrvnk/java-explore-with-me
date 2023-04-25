package ru.practicum.main.controller.adminapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.category.NewCategoryDto;

public interface AdminCategoryApi extends AdminApi {
    @PostMapping("/categories")
    ResponseEntity<CategoryDto> createCategory(@RequestBody NewCategoryDto newCategoryDto);

    @PatchMapping("/categories/{id}")
    ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Long id,
                                               @RequestBody CategoryDto categoryDto);

    @DeleteMapping("/categories/{id}")
    void deleteCategory(@PathVariable("id") Long id);

}
