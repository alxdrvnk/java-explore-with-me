package ru.practicum.main.controller.adminapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.category.NewCategoryDto;
import ru.practicum.main.dto.user.NewUserRequest;
import ru.practicum.main.dto.user.UserDto;
import ru.practicum.main.mapper.category.CategoryMapper;
import ru.practicum.main.mapper.user.UserMapper;
import ru.practicum.main.model.user.User;
import ru.practicum.main.service.category.CategoryService;
import ru.practicum.main.service.user.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi{

    private final UserService userService;
    private final CategoryService categoryService;
    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<UserDto> createUser(NewUserRequest newUserRequest) {
        User user = userService.createUser(userMapper.toUser(newUserRequest));
        return new ResponseEntity<>(
                userMapper.toUserDto(user), HttpStatus.CREATED);

    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        return userMapper.toUserDtoList(userService.getUsers(ids, from, size));
    }

    @Override
    public void deleteUser(Long id) {
        userService.deleteUserById(id);
    }
}
