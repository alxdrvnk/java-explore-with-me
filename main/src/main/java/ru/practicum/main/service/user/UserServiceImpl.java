package ru.practicum.main.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.exception.EwmAlreadyExistsException;
import ru.practicum.main.exception.EwmInternalServerException;
import ru.practicum.main.exception.EwmNotFoundException;
import ru.practicum.main.model.user.User;
import ru.practicum.main.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j(topic = "Admin User Service")
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    //TODO: Возможно стоит передалать метод
    public User createUser(User user) {
        log.info("Create new User: {}", user);
        Optional<User> createdUser;

        try {
            createdUser = userRepository.findByEmail(user.getEmail());
        } catch (Exception e) {
            log.error("Failed to create User: {}", user, e);
            throw new EwmInternalServerException(String.format(
                    "Failed to create user %s", user.getEmail()),e);
        }

        if (createdUser.isPresent()) {
            log.info("User {} already exists", user);
            throw new EwmAlreadyExistsException(String.format(
                    "User with Email %s already exists", user.getEmail()));
        }

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Failed to create user {}", user, e);
            throw new EwmInternalServerException(String.format(
                    "Failed to create user %s", user.getEmail()), e);
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
