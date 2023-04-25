package ru.practicum.main.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.model.event.EventState;
import ru.practicum.main.model.user.User;

import java.util.List;
import java.util.Optional;

public interface EventRepositoy extends JpaRepository<Event, Long> {
    List<Event> findByInitiator(User user, PageRequest pageRequest);

    Optional<Event> findByIdAndStateIs(Long id, EventState state);

    Optional<Event> findByIdAndInitiato(Long id, User user);
}
