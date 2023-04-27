package ru.practicum.main.service.event;

import ru.practicum.main.dto.event.EventRequestStatusUpdateResult;
import ru.practicum.main.dto.event.EventSearchFilter;
import ru.practicum.main.dto.event.UpdateEvenAdminRequest;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.model.event.EventRequestStatusUpdateRequest;
import ru.practicum.main.model.request.ParticipationRequest;

import java.util.Collection;

public interface EventService {
    Collection<Event> getAllEvents(EventSearchFilter eventSearchFilter);

    Event updateEventByAdmin(Long id, UpdateEvenAdminRequest updateRequest);

    Event createEvent(Long userId, Event event);

    Event updateEventById(Long userId, Long eventId, Event event);

    Collection<Event> getEventByUser(Long userId, Integer from, Integer size);

    Event getEventByIdPrivate(Long userId, Long eventId);

    Event getEventByIdPublic(Long id, String uri, String ip);

    Collection<ParticipationRequest> getEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateEventRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest request);

    Collection<Event> getAllEvents(EventSearchFilter filter, String uri, String api);
}