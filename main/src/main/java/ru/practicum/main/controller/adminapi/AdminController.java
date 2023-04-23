package ru.practicum.main.controller.adminapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.category.NewCategoryDto;
import ru.practicum.main.dto.user.NewUserRequest;
import ru.practicum.main.dto.user.UserDto;
import ru.practicum.main.mapper.UserMapper;
import ru.practicum.main.model.User;
import ru.practicum.main.service.admin.AdminUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi{

    private final AdminUserService adminUserService;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<UserDto> createUser(NewUserRequest newUserRequest) {
        User user = adminUserService.createUser(userMapper.toUser(newUserRequest));
        return new ResponseEntity<>(
                userMapper.toUserDto(user), HttpStatus.CREATED);

    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        return userMapper.toUserDtoList(adminUserService.getUsers(ids, from, size));
    }

    @Override
    public void deleteUser(Long id) {
        adminUserService.deleteUserById(id);
    }

    @Override
    public ResponseEntity<CategoryDto> createCategory(NewCategoryDto newCategoryDto) {
        return null;
    }

    @Override
    public void deleteCategorie(Long id) {

    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        return null;
    }
}
