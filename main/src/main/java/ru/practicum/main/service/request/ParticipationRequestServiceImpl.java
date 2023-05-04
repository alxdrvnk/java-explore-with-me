package ru.practicum.main.service.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.practicum.main.exception.EwmAlreadyExistsException;
import ru.practicum.main.exception.EwmIllegalArgumentException;
import ru.practicum.main.exception.EwmNotFoundException;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.model.event.EventState;
import ru.practicum.main.model.request.ParticipationRequest;
import ru.practicum.main.model.request.RequestStatus;
import ru.practicum.main.model.user.User;
import ru.practicum.main.repository.RequestRepository;
import ru.practicum.main.service.event.EventService;
import ru.practicum.main.service.user.UserService;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Slf4j(topic = "Participation Request Service")
@Service
@RequiredArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final RequestRepository requestRepository;
    private final UserService userService;
    @Lazy
    private final EventService eventService;
    private final Clock clock;

    @Override
    public Collection<ParticipationRequest> getRequestsByUser(Long userId) {
        User user = userService.getUserById(userId);
        return requestRepository.findByRequester(user);
    }

    @Override
    @Transactional
    public ParticipationRequest createRequest(Long userId, Long eventId) {
        log.info("User with id: {} create Request on event with id: {}", userId, eventId);
        User user = userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);
        if (event.getParticipantLimit() == event.getConfirmedRequests() && event.getParticipantLimit() != 0) {
            throw new EwmAlreadyExistsException(
                    String.format("Event with id: %d reached the participation limit",
                            event.getId()));
        }
        if (requestRepository.existsByRequesterAndEvent(user, event)) {
            throw new EwmAlreadyExistsException(
                    String.format("User with id: %d is already send request to event with id: %d",
                            user.getId(), event.getId()));
        }
        validateEvent(event, user);
        ParticipationRequest request = ParticipationRequest.builder()
                .event(event)
                .requester(user)
                .createdDate(LocalDateTime.now(clock))
                .status(event.getModeration() ? RequestStatus.PENDING : RequestStatus.CONFIRMED).build();

        if (!event.getModeration()) {
            eventService.increaseConfirmedRequests(event);
        }
        return requestRepository.save(request);
    }

    @Override
    @Transactional
    public ParticipationRequest cancelRequest(Long userId, Long requestId) {
        log.info("User with id: {} cancel Request with id: {}",userId, requestId);
        userService.getUserById(userId);
        ParticipationRequest request = requestRepository.findById(requestId).orElseThrow(
                () -> new EwmNotFoundException(String.format("Participation Request with id: %d not found", userId)));

        if (!request.getRequester().getId().equals(userId)) {
            throw new EwmIllegalArgumentException(String.format("User with id: %d doesn't create Request with id: %d",
                    userId, requestId));
        }

        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            eventService.decreaseConfirmRequests(request.getEvent());
        }

        request = request.withStatus(RequestStatus.CANCELED);

        return requestRepository.save(request);
    }

    @Override
    public Collection<ParticipationRequest> getAllByIds(List<Long> requestIds) {
        return requestRepository.findAllById(requestIds);
    }

    @Override
    public void updateRequests(Collection<ParticipationRequest> eventRequests) {
        requestRepository.saveAll(eventRequests);
    }

    private void validateEvent(Event event, User user) {

        if (event.getInitiator().getId().equals(user.getId())) {
            throw new EwmIllegalArgumentException(String.format("User with id: %d is initiator", user.getId()));
        }

        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() == event.getParticipantLimit()) {
            throw new EwmIllegalArgumentException(String.format("No vacancies left for Event with id: %d",
                    event.getId()));
        }

        if (event.getState() != EventState.PUBLISHED) {
            throw new EwmIllegalArgumentException(String.format("Event with id: %d not published", event.getId()));
        }
    }
}
