package ru.practicum.main.service.admin;

import ru.practicum.main.model.User;

import java.util.List;

public interface AdminService {
     User createUser(User user);
     User getUserById(Long id);

     List<User> getUsers(List<Long> ids, int from, int size);

     void deleteUserById(Long id);
}
