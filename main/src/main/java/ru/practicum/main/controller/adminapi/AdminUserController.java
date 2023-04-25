package ru.practicum.main.controller.adminapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.dto.user.NewUserRequest;
import ru.practicum.main.dto.user.UserDto;
import ru.practicum.main.mapper.user.UserMapper;
import ru.practicum.main.model.user.User;
import ru.practicum.main.service.user.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminUserController implements AdminUserApi {

    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<UserDto> createUser(NewUserRequest newUserRequest) {
        User user = userService.createUser(userMapper.toUser(newUserRequest));
        return new ResponseEntity<>(
                userMapper.toUserDto(user), HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<Collection<UserDto>> getUsers(List<Long> ids, Integer from, Integer size) {
        return ResponseEntity.ok(userMapper.toUserDtoList(userService.getUsers(ids, from, size)));
    }

    @Override
    public void deleteUser(Long id) {
        userService.deleteUserById(id);
    }
}
