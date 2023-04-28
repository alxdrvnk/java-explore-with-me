package ru.practicum.main.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.dto.event.EventRequestStatusUpdateResult;
import ru.practicum.main.dto.event.EventSearchFilter;
import ru.practicum.main.dto.event.UpdateEvenAdminRequest;
import ru.practicum.main.exception.EwmNotFoundException;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.model.event.EventRequestStatusUpdateRequest;
import ru.practicum.main.model.request.ParticipationRequest;
import ru.practicum.main.repository.EventRepositoy;

import javax.transaction.Transactional;
import java.util.Collection;

@Slf4j(topic = "Event Service")
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepositoy eventRepositoy;

    @Override
    public Collection<Event> getAllEvents(EventSearchFilter eventSearchFilter) {
        return null;
    }

    @Override
    public Event updateEventByAdmin(Long id, UpdateEvenAdminRequest updateRequest) {
        return null;
    }

    @Override
    public Event createEvent(Long userId, Event event) {
        return null;
    }

    @Override
    public Event updateEventById(Long userId, Long eventId, Event event) {
        return null;
    }

    @Override
    public Collection<Event> getEventByUser(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public Event getEventByIdPrivate(Long userId, Long eventId) {
        return null;
    }

    @Override
    public Event getEventByIdPublic(Long id, String uri, String ip) {
        return null;
    }

    @Override
    public Collection<ParticipationRequest> getEventRequests(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult updateEventRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest request) {
        return null;
    }

    @Override
    //TODO: add stats client
    public Collection<Event> getAllEvents(EventSearchFilter filter, String uri, String api) {
        return null;
    }

    @Override
    public Event getEventById(Long eventId) {
        return eventRepositoy.findById(eventId).orElseThrow(
                () -> new EwmNotFoundException(String.format("Event wih id: %d not found", eventId)));
    }

    @Override
    @Transactional
    public void decreaseConfirmRequests(Event event) {
        log.info("Decrease count of Confirmed requests for Event with id: {}",event.getId());
        if (event.getConfirmedRequests() > 0) {
            eventRepositoy.save(event.withConfirmedRequests(event.getConfirmedRequests() - 1));
        }
    }

    @Override
    @Transactional
    public void increaseConfirmedRequests(Event event) {
        log.info("Increase confirmed Requests for Event with id: {}", event.getId());
        if (event.getParticipantLimit() == 0 || event.getParticipantLimit() != event.getConfirmedRequests()) {
            eventRepositoy.save(event.withConfirmedRequests(event.getConfirmedRequests() + 1));
        }
    }

}
