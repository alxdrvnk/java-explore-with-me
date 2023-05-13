package ru.practicum.main.controller.publicapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.main.dto.category.CategoryDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RequestMapping("/categories")
public interface PublicCategoryApi {

    @GetMapping
    ResponseEntity<Collection<CategoryDto>> getAllCategories(@RequestParam(defaultValue = "0")
                                                             @PositiveOrZero Integer from,
                                                             @RequestParam(defaultValue = "10")
                                                             @Positive Integer size);

    @GetMapping("/{id}")
    ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long id);
}
