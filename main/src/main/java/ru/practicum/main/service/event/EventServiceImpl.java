package ru.practicum.main.service.event;

import ru.practicum.main.dto.event.EventSearchFilter;
import ru.practicum.main.dto.event.UpdateEvenAdminRequest;
import ru.practicum.main.model.event.Event;

import java.util.Collection;

public class EventServiceImpl implements EventService {
    @Override
    public Collection<Event> getAllEvents(EventSearchFilter eventSearchFilter) {
        return null;
    }

    @Override
    public Event updateEventByAdmin(Long id, UpdateEvenAdminRequest updateRequest) {
        return null;
    }
}
