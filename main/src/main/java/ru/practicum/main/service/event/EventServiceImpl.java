package ru.practicum.main.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main.configuration.ClockConfig;
import ru.practicum.main.converter.DateTimeConverter;
import ru.practicum.main.dto.event.EventSearchFilter;
import ru.practicum.main.exception.EwmIllegalArgumentException;
import ru.practicum.main.exception.EwmNotFoundException;
import ru.practicum.main.mapper.event.EventMapper;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.model.event.*;
import ru.practicum.main.model.request.ParticipationRequest;
import ru.practicum.main.model.request.RequestStatus;
import ru.practicum.main.model.user.User;
import ru.practicum.main.repository.EventRepository;
import ru.practicum.main.repository.RequestRepository;
import ru.practicum.main.repository.specification.EventSpecification;
import ru.practicum.main.service.category.CategoryService;
import ru.practicum.main.service.user.UserService;
import ru.practicum.stats.client.StatsClient;
import ru.practicum.stats.dto.ViewStatsDto;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j(topic = "Event Service")
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final StatsClient statsClient;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryService categoryService;
    private final UserService userService;
    private final RequestRepository requestRepository;
    private final Clock clock;

    @Value("${app.name}")
    private String appName;
    @Override
    public Collection<Event> getAllEvents(EventSearchFilter eventSearchFilter) {
        EventSpecification spec = new EventSpecification(eventSearchFilter,
                new DateTimeConverter(), new ClockConfig().clock());
        PageRequest pageRequest = PageRequest.of(eventSearchFilter.getFrom() / eventSearchFilter.getSize(),
                eventSearchFilter.getSize());
        return eventRepository.findAll(spec, pageRequest).getContent();
    }

    @Override
    @Transactional
    public Event updateEventByAdmin(Long id, UpdateEventRequest updateRequest) {
        validateEventDate(updateRequest.getEventDate());
        Event event = getEventById(id);
        if (event.getState() == EventState.PUBLISHED && updateRequest.getEventState() == EventState.PUBLISHED) {
            throw new EwmIllegalArgumentException(
                    String.format("Event with id: %d is already published", event.getId()));

        } else if (event.getState() == EventState.PUBLISHED && updateRequest.getEventState() == EventState.REJECTED) {
            throw new EwmIllegalArgumentException(
                    String.format("Event with id: %d is already published", event.getId()));

        } else if (event.getState() == EventState.REJECTED && updateRequest.getEventState() != null) {
            throw new EwmIllegalArgumentException(
                    String.format("Event with id: %d is already canceled", event.getId()));
        }
        Category category = updateRequest.getCategoryId() == null ?
                event.getCategory()
                : categoryService.getById(updateRequest.getCategoryId());
        return eventRepository.save(eventMapper.partialEventUpdate(event, category, updateRequest));
    }

    @Override
    @Transactional
    public Event createEvent(Long userId, NewEventRequest eventRequest) {
        validateEventDate(eventRequest.getEventDate());
        User user = userService.getUserById(userId);
        Category category = categoryService.getById(eventRequest.getCategoryId());
        return eventRepository.save(eventMapper.toEvent(user, category, eventRequest));
    }

    @Override
    @Transactional
    public Event updateEventById(Long userId, Long eventId, UpdateEventRequest updateRequest) {
        validateEventDate(updateRequest.getEventDate());
        userService.getUserById(userId);
        Event event = getEventById(eventId);
        if (userId != event.getInitiator().getId()) {
            throw new EwmIllegalArgumentException(
                    String.format("User with id: %d not initiator of Event with id: %d", userId, eventId));
        }

        if (event.getState() == EventState.PUBLISHED) {
            throw new EwmIllegalArgumentException("Cannot edit Event when is Published");
        }

        return eventRepository.save(eventMapper.partialEventUpdate(event, updateRequest));
    }

    @Override
    public Collection<Event> getEventByUser(Long userId, Integer from, Integer size) {
        User user = userService.getUserById(userId);
        return eventRepository.findByInitiator(user, PageRequest.of(from / size, size));
    }

    @Override
    public Event getEventByIdPrivate(Long userId, Long eventId) {
        User user = userService.getUserById(userId);
        return eventRepository.findByIdAndInitiator(eventId, user).orElseThrow(() ->
                new EwmNotFoundException(
                        String.format("Event with id: %d do not belong to User with id: %d", eventId, userId)));
    }

    @Override
    public Event getEventByIdPublic(Long id, String uri, String ip) {
        statsClient.saveRequest(appName, uri, ip, LocalDateTime.now(clock));
        Event event = eventRepository.findByIdAndStateIs(id, EventState.PUBLISHED).orElseThrow(() ->
                new EwmNotFoundException(String.format("Event with id: %d not found or it not published", id)));
        return getViewsStat(List.of(event), uri).stream().findFirst().get();
    }

    @Override
    public Collection<ParticipationRequest> getEventRequests(Long userId, Long eventId) {
        User user = userService.getUserById(userId);
        Event event = getEventById(eventId);
        if (event.getInitiator().getId() != userId) {
            throw new EwmIllegalArgumentException(String.format("User with id: %d not initiator", userId));
        }
        return requestRepository.findByRequester(user);
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateEventRequests(Long userId,
                                                              Long eventId,
                                                              EventRequestStatusUpdateRequest request) {
        userService.getUserById(userId);
        Event event = getEventById(eventId);
        int confirmsCount = event.getConfirmedRequests();
        Collection<ParticipationRequest> eventRequests = requestRepository.findAllById(request.getRequestIds());
        Set<ParticipationRequest> confirmedRequests = new HashSet<>();
        Set<ParticipationRequest> rejectedRequests = new HashSet<>();
        if (event.getModeration().equals(Boolean.TRUE) || event.getParticipantLimit() != 0) {
            for (ParticipationRequest er : eventRequests) {
                if (!er.getStatus().equals(RequestStatus.PENDING)) {
                    throw new EwmIllegalArgumentException(
                            String.format("Request with id: %d must be in \"PENDING\" status", er.getId()));
                }
                if (confirmsCount != event.getParticipantLimit()
                        && request.getStatus().equals(RequestStatus.CONFIRMED)) {
                    er = er.withStatus(RequestStatus.CONFIRMED);
                    confirmedRequests.add(er);
                    confirmsCount += 1;
                } else {
                    er = er.withStatus(RequestStatus.REJECTED);
                    rejectedRequests.add(er);
                }
            }
        }
        event = event.withConfirmedRequests(confirmsCount);

        requestRepository.saveAll(eventRequests);
        eventRepository.save(event);
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmedRequests)
                .rejectedRequests(rejectedRequests).build();
    }

    @Override
    public Collection<Event> getAllEvents(EventSearchFilter filter, String uri, String ip) {
        statsClient.saveRequest(appName, uri, ip, LocalDateTime.now(clock));
        PageRequest pageRequest = PageRequest.of(filter.getFrom() / filter.getSize(), filter.getSize());
        EventSpecification spec = new EventSpecification(filter,
                new DateTimeConverter(), new ClockConfig().clock());

        List<Event> eventList = eventRepository.findAll(spec, pageRequest).getContent();

        if (eventList.isEmpty()) {
            return Collections.emptyList();
        }

        return getViewsStat(eventList, uri);
    }

    @Override
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new EwmNotFoundException(String.format("Event wih id: %d not found", eventId)));
    }

    @Override
    @Transactional
    public void decreaseConfirmRequests(Event event) {
        log.info("Decrease count of Confirmed requests for Event with id: {}",event.getId());
        if (event.getConfirmedRequests() > 0) {
            eventRepository.save(event.withConfirmedRequests(event.getConfirmedRequests() - 1));
        }
    }

    @Override
    @Transactional
    public void increaseConfirmedRequests(Event event) {
        log.info("Increase confirmed Requests for Event with id: {}", event.getId());
        if (event.getParticipantLimit() == 0 || event.getParticipantLimit() != event.getConfirmedRequests()) {
            eventRepository.save(event.withConfirmedRequests(event.getConfirmedRequests() + 1));
        }
    }

    private void validateEventDate(LocalDateTime eventDate) {
        if (eventDate != null && eventDate.isBefore(LocalDateTime.now(clock).plusHours(1))) {
            throw new EwmIllegalArgumentException("Start date must be least 1 hour before");
        }
    }

    private Collection<Event> getViewsStat(List<Event> eventList, String uri) {
        List<String> uris = eventList.stream().map(event -> uri + "/" + event.getId()).collect(Collectors.toList());
        LocalDateTime start = LocalDateTime.MIN; //Возможно стоит вынести в отдельную переменную
        List<ViewStatsDto> stats = statsClient.getStats(start, LocalDateTime.now(clock), uris, false);

        Map<String, Long> mappedStatsByUri = stats.stream()
                .collect(Collectors.toMap(ViewStatsDto::getUri, ViewStatsDto::getHits));

        return eventList.stream()
                .map(event -> event.withViews(
                        mappedStatsByUri.get(uri + "/" + event.getId()).intValue())).collect(Collectors.toList());
    }
}