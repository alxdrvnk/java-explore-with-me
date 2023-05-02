package ru.practicum.main.controller.adminapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.category.NewCategoryDto;

import javax.validation.Valid;

public interface AdminCategoryApi extends AdminApi {
    @PostMapping("/categories")
    ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto);

    @PatchMapping("/categories/{id}")
    ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Long id,
                                               @RequestBody @Valid CategoryDto categoryDto);

    @DeleteMapping("/categories/{id}")
    ResponseEntity<Object> deleteCategory(@PathVariable("id") Long id);

}
