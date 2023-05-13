package ru.practicum.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.model.request.ParticipationRequest;
import ru.practicum.main.model.user.User;

import java.util.Collection;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {
    Collection<ParticipationRequest> findByRequester(User user);

    boolean existsByRequesterAndEvent(User initiator, Event event);

    Collection<ParticipationRequest> findAllByEvent(Event event);
}
