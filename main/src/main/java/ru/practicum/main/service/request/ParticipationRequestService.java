package ru.practicum.main.service.request;

import ru.practicum.main.model.request.ParticipationRequest;

import java.util.Collection;
import java.util.List;

public interface ParticipationRequestService {
    Collection<ParticipationRequest> getRequestsByUser(Long userId);

    ParticipationRequest createRequest(Long userId, Long eventId);

    ParticipationRequest cancelRequest(Long userId, Long eventId);

    Collection<ParticipationRequest> getAllByIds(List<Long> requestIds);

    void updateRequests(Collection<ParticipationRequest> eventRequests);
}
