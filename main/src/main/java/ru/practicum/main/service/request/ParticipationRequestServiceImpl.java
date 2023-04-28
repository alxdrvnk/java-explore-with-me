package ru.practicum.main.service.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.exception.EwmIlligalArgumentException;
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

@Slf4j(topic = "Participation Request Service")
@Service
@RequiredArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService{

    private final RequestRepository requestRepository;
    private final UserService userService;
    private final EventService eventService;
    private final Clock clock;

    @Override
    public Collection<ParticipationRequest> getRequestsByUser(Long userId) {
        User user = userService.getUserById(userId);
        return requestRepository.findByRequester(user);
    }

    @Override
    @Transactional
    //TODO: Возможно стоит вынести провеку события в отдельный метод
    public ParticipationRequest createRequest(Long userId, Long eventId) {
        log.info("User with id: {} create Request on event with id: {}", userId, eventId);

        User user = userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);
        if (event.getInitiator().getId() == user.getId()) {
            throw new EwmIlligalArgumentException(String.format("User with id: %d is initiator", userId));
        }

        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() == event.getParticipantLimit()) {
            throw new EwmIlligalArgumentException(String.format("No vacancies left for Event with id: %d", eventId));
        }

        if (event.getState() == EventState.PUBLISHED){
            throw new EwmIlligalArgumentException(String.format("Event with id: %d not published", eventId));
        }

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
        log.info("User with id: {} cancel Request with id:{}",userId, requestId);
        userService.getUserById(userId);
        ParticipationRequest request = requestRepository.findById(requestId).orElseThrow(
                () -> new EwmNotFoundException(String.format("Participation Request with id: %d not found", userId)));

        if (request.getRequester().getId() != userId){
            throw new EwmIlligalArgumentException(String.format("User with id: %d doesn't create Request with id: %d",
                    userId, requestId));
        }

        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            eventService.decreaseConfirmRequests(request.getEvent());
        }

        request = request.withStatus(RequestStatus.CANCELED);

        return requestRepository.save(request);
    }
}
