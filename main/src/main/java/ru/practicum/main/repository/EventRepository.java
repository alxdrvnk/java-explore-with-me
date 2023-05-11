package ru.practicum.main.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.model.event.EventState;
import ru.practicum.main.model.user.User;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    @Query(value =
            "SELECT new ru.practicum.main.model.event.Event(" +
                    "e.id," +
                    "e.annotation," +
                    "e.category," +
                    "COUNT(pr.id)," +
                    "e.createdDate," +
                    "e.description," +
                    "e.eventDate," +
                    "e.initiator," +
                    "e.location," +
                    "e.paid," +
                    "e.participantLimit," +
                    "e.publishedDate," +
                    "e.moderation," +
                    "e.state," +
                    "e.title) FROM Event As e " +
                    "LEFT JOIN ParticipationRequest As pr " +
                    "ON pr.event.id = e.id AND pr.status = 'CONFIRMED' " +
                    "WHERE e.id = :id " +
                    "GROUP BY e.id")
    Optional<Event> findById(Long id);

    @Query(value =
            "SELECT new ru.practicum.main.model.event.Event(" +
                    "e.id," +
                    "e.annotation," +
                    "e.category," +
                    "COUNT(pr.id)," +
                    "e.createdDate," +
                    "e.description," +
                    "e.eventDate," +
                    "e.initiator," +
                    "e.location," +
                    "e.paid," +
                    "e.participantLimit," +
                    "e.publishedDate," +
                    "e.moderation," +
                    "e.state," +
                    "e.title) FROM Event As e " +
            "LEFT JOIN ParticipationRequest As pr " +
            "ON pr.event.id = e.id AND pr.status = 'CONFIRMED' " +
            "WHERE e.initiator = :user " +
            "GROUP BY e.id")
    List<Event> findByInitiator(User user, PageRequest pageRequest);

    @Query(value =
            "SELECT new ru.practicum.main.model.event.Event(" +
                    "e.id," +
                    "e.annotation," +
                    "e.category," +
                    "COUNT(pr.id)," +
                    "e.createdDate," +
                    "e.description," +
                    "e.eventDate," +
                    "e.initiator," +
                    "e.location," +
                    "e.paid," +
                    "e.participantLimit," +
                    "e.publishedDate," +
                    "e.moderation," +
                    "e.state," +
                    "e.title) FROM Event As e " +
                    "LEFT JOIN ParticipationRequest As pr " +
                    "ON pr.event.id = e.id AND pr.status = 'CONFIRMED' " +
                    "WHERE e.state = :state AND e.id = :id " +
                    "GROUP BY e.id")
    Optional<Event> findByIdAndStateIs(Long id, EventState state);

    @Query(value =
            "SELECT new ru.practicum.main.model.event.Event(" +
                    "e.id," +
                    "e.annotation," +
                    "e.category," +
                    "COUNT(pr.id)," +
                    "e.createdDate," +
                    "e.description," +
                    "e.eventDate," +
                    "e.initiator," +
                    "e.location," +
                    "e.paid," +
                    "e.participantLimit," +
                    "e.publishedDate," +
                    "e.moderation," +
                    "e.state," +
                    "e.title) FROM Event As e " +
                    "LEFT JOIN ParticipationRequest As pr " +
                    "ON pr.event.id = e.id AND pr.status = 'CONFIRMED' " +
                    "WHERE e.initiator = :user AND e.id = :id " +
                    "GROUP BY e.id")
    Optional<Event> findByIdAndInitiator(Long id, User user);


    @Query(
            "SELECT e.id, COUNT(pr.id) FROM Event AS e " +
                    "LEFT JOIN ParticipationRequest As pr " +
                    "ON pr.event.id = e.id AND pr.status = 'CONFIRMED' " +
                    "WHERE e.id IN :eventIds " +
                    "GROUP BY e.id"
    )
    List<Object[]> getConfirmedRequestCountForEvents(List<Long> eventIds);
}
