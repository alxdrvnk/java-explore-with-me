package ru.practicum.main.controller.privateapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.dto.request.ParticipationRequestDto;
import ru.practicum.main.mapper.request.RequestMapper;
import ru.practicum.main.service.request.ParticipationRequestService;

import java.util.Collection;

@Slf4j(topic = "Private Request Controller")
@RestController
@RequiredArgsConstructor
public class PrivateRequestController implements PrivateRequestApi {

    private final ParticipationRequestService requestService;
    private final RequestMapper requestMapper;

    @Override
    public ResponseEntity<Collection<ParticipationRequestDto>> getRequestsByUser(Long userId) {
        return ResponseEntity.ok(
                requestMapper.toParticipationRequestDtoList(requestService.getRequestsByUser(userId)));
    }

    @Override
    public ResponseEntity<ParticipationRequestDto> createRequest(Long userId, Long eventId) {
        log.info("User with id: {} create Participation Request for Event with id: {}", userId, eventId);
        return new ResponseEntity<>(
                requestMapper.toParticipationRequestDto(requestService.createRequest(userId, eventId)),
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ParticipationRequestDto> cancelRequest(Long userId, Long requestId) {
        log.info("User with id: {} cancel Participation Request with id: {}", userId, requestId);
        return ResponseEntity.ok(
                requestMapper.toParticipationRequestDto(requestService.cancelRequest(userId, requestId)));
    }
}
