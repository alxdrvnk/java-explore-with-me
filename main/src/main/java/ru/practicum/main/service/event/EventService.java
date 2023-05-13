package ru.practicum.main.service.event;

import ru.practicum.main.dto.event.EventSearchFilter;
import ru.practicum.main.model.event.*;
import ru.practicum.main.model.request.ParticipationRequest;

import java.util.Collection;
import java.util.List;

public interface EventService {
    Collection<Event> getAllEvents(EventSearchFilter eventSearchFilter);

    Event updateEventByAdmin(Long id, UpdateEventRequest updateRequest);

    Event createEvent(Long userId, NewEventRequest eventRequest);

    Event updateEventById(Long userId, Long eventId, UpdateEventRequest updateRequest);

    Collection<Event> getEventByUser(Long userId, Integer from, Integer size);

    Event getEventByIdPrivate(Long userId, Long eventId);

    Event getEventByIdPublic(Long id, String uri, String ip);

    Collection<ParticipationRequest> getEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateEventRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest request);

    Collection<Event> getAllEvents(EventSearchFilter filter, String uri, String ip);

    Event getEventById(Long eventId);

    void decreaseConfirmRequests(Event event);

    void increaseConfirmedRequests(Event event);

    List<Event> getAllEventsByIds(Collection<Long> eventsId);
}