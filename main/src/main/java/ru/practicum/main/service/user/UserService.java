package ru.practicum.main.service.user;

import ru.practicum.main.model.user.User;

import java.util.List;

public interface UserService {
     User createUser(User user);

     User getUserById(Long id);

     List<User> getUsers(List<Long> ids, int from, int size);

     void deleteUserById(Long id);



}
