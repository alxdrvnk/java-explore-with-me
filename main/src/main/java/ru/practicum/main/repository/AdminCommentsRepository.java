package ru.practicum.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.model.comment.AdminEventComment;
import ru.practicum.main.model.event.Event;

import java.util.List;

public interface AdminCommentsRepository extends JpaRepository<AdminEventComment, Long> {

    List<AdminEventComment> findAllByEventAndCorrectedOrderByCreatedDateDesc(Event event, boolean corrected);

    List<AdminEventComment> findAllByEventIdInAndCorrected(List<Long> eventsId, boolean corrected);

}
