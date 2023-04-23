package ru.practicum.main.controller.adminapi;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.category.NewCategoryDto;
import ru.practicum.main.dto.user.NewUserRequest;
import ru.practicum.main.dto.user.UserDto;

import java.util.List;

@RequestMapping("/admin")
public interface AdminApi {

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> createUser(@RequestBody NewUserRequest newUserRequest);

    @GetMapping("/users")
    List<UserDto> getUsers(@RequestParam List<Long> ids,
                           @RequestParam(defaultValue = "0") Integer from,
                           @RequestParam(defaultValue = "10") Integer size);

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") Long id);

    @PostMapping("/categories")
    ResponseEntity<CategoryDto> createCategory(@RequestBody NewCategoryDto newCategoryDto);

    @DeleteMapping("/categories/{id}")
    void deleteCategorie(@PathVariable("id") Long id);

    @PatchMapping("/categories/{id}")
    CategoryDto updateCategory(@PathVariable("id") Long id,
                               @RequestBody CategoryDto categoryDto);
}
