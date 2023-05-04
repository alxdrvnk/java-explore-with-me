package ru.practicum.main.controller.adminapi;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.user.NewUserRequest;
import ru.practicum.main.dto.user.UserDto;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

public interface AdminUserApi extends AdminApi {

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> createUser(@RequestBody @Valid NewUserRequest newUserRequest);

    @GetMapping("/users")
    ResponseEntity<Collection<UserDto>> getUsers(@RequestParam List<Long> ids,
                                                 @RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "10") Integer size);

    @DeleteMapping("/users/{id}")
    ResponseEntity<Object> deleteUser(@PathVariable("id") Long id);
}
