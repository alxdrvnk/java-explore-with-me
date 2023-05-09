package ru.practicum.main.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.NonUniqueObjectException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.exception.EwmAlreadyExistsException;
import ru.practicum.main.exception.EwmNotFoundException;
import ru.practicum.main.model.user.User;
import ru.practicum.main.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j(topic = "User Service")
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(User user) {
        log.info("Create new User: {}", user);
        try {
            return userRepository.save(user);
        } catch (NonUniqueObjectException e) {
            throw new EwmAlreadyExistsException(String.format(
                    "User with email %s already exists", user.getEmail()));
        }
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EwmNotFoundException(String.format("User with Id %d not found", id)));
    }

    @Override
    public List<User> getUsers(List<Long> ids, int from, int size) {
        return ids == null ?
                userRepository.findAll(PageRequest.of(from / size, size)).getContent() :
                userRepository.findAllById(ids);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

}
