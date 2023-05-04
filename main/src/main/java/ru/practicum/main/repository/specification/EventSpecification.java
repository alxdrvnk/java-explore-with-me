package ru.practicum.main.repository.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.main.converter.DateTimeConverter;
import ru.practicum.main.dto.event.EventSearchFilter;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.model.event.EventState;

import javax.persistence.criteria.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EventSpecification implements Specification<Event> {

    private final EventSearchFilter filter;
    private final DateTimeConverter converter;
    private final Clock clock;


    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Order order;

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getText() != null) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("annotation")),
                            "%" + filter.getText().toLowerCase() + "%"));
        }
        if (filter.getUsers() != null) {
            predicates.add(root.get("initiator").in(Arrays.asList(filter.getUsers())));
        }
        if (filter.getStates() != null) {
            predicates.add(root.get("state")
                    .in(Arrays.stream(filter.getStates()).map(EventState::valueOf).collect(Collectors.toList())));
        }
        if (filter.getCategories() != null) {
            predicates.add(root.get("category").in(Arrays.asList(filter.getCategories())));
        }
        if (filter.getRangeStart() != null && filter.getRangeEnd() != null) {
            predicates.add(
                    criteriaBuilder.between(root.get("eventDate"), converter.parseDate(filter.getRangeStart()), converter.parseDate(filter.getRangeEnd())));
        } else if (filter.getRangeStart() != null) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), converter.parseDate(filter.getRangeStart())));
        } else if (filter.getRangeEnd() != null) {
            predicates.add(
                    criteriaBuilder.between(
                            root.get("eventDate"), LocalDateTime.now(clock),
                            converter.parseDate(filter.getRangeEnd())));
        } else {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(
                            root.get("eventDate"),
                            LocalDateTime.now(clock)));
        }
        if (filter.getOnlyAvailable()) {
            predicates.add(
                    criteriaBuilder.lessThan(root.get("confirmedRequests"), root.get("participantLimit")));
        }

        if (filter.getPaid() != null) {
            predicates.add(
                    criteriaBuilder.equal(root.get("paid"), filter.getPaid()));
        }

        if (filter.getSort() != null) {
            if (filter.getSort().equals("VIEWS")) {
                order = criteriaBuilder.desc(root.get("views"));
            } else {
                order = criteriaBuilder.desc(root.get("eventDate"));
            }
            query.orderBy(order);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
