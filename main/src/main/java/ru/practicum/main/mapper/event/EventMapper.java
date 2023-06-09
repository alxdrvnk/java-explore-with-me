package ru.practicum.main.mapper.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.main.converter.DateTimeConverter;
import ru.practicum.main.dto.event.*;
import ru.practicum.main.exception.EwmIllegalArgumentException;
import ru.practicum.main.mapper.category.CategoryMapper;
import ru.practicum.main.mapper.comment.CommentMapper;
import ru.practicum.main.mapper.user.UserMapper;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.model.event.*;
import ru.practicum.main.model.request.RequestStatus;
import ru.practicum.main.model.user.User;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.practicum.main.dto.event.UpdateEventAdminRequestDto.StateAction.PUBLISH_EVENT;
import static ru.practicum.main.dto.event.UpdateEventAdminRequestDto.StateAction.REJECT_EVENT;
import static ru.practicum.main.dto.event.UpdateEventUserRequestDto.StateAction.CANCEL_REVIEW;
import static ru.practicum.main.dto.event.UpdateEventUserRequestDto.StateAction.SEND_TO_REVIEW;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final CategoryMapper categoryMapper;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    private final Clock clock;

    public EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(DateTimeConverter.formatDate(event.getCreatedDate()))
                .description(event.getDescription())
                .eventDate(DateTimeConverter.formatDate(event.getEventDate()))
                .initiator(userMapper.toUserShortDto(event.getInitiator()))
                .location(this.toLocationDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedDate() == null ?
                        null : event.getPublishedDate().toString())
                .requestModeration(event.getModeration())
                .state(event.getState().name())
                .title(event.getTitle())
                .views(event.getViews())
                .build();

    }

    public EventFullWithCommentsDto toEventFullWithCommentsDto(Event event) {
        return EventFullWithCommentsDto.EventFullWithCommentsDtoBuilder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(DateTimeConverter.formatDate(event.getCreatedDate()))
                .description(event.getDescription())
                .eventDate(DateTimeConverter.formatDate(event.getEventDate()))
                .initiator(userMapper.toUserShortDto(event.getInitiator()))
                .location(this.toLocationDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedDate() == null ?
                        null : event.getPublishedDate().toString())
                .requestModeration(event.getModeration())
                .state(event.getState().name())
                .title(event.getTitle())
                .views(event.getViews())
                .comments(
                        commentMapper.toCommentDtoList(event.getComments()))
                .build();
    }

    public EventShortDto toEventShortDto(Event event)  {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(DateTimeConverter.formatDate(event.getEventDate()))
                .initiator(userMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public EventShortWithCommentsDto toEventShortWithCommentsDto(Event event) {
        return EventShortWithCommentsDto.EventShortWithCommentsDtoBuilder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(DateTimeConverter.formatDate(event.getEventDate()))
                .initiator(userMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .comments(
                        commentMapper.toCommentDtoList(event.getComments()))
                .build();
    }

    public Collection<EventShortDto> toEventShortDtoList(Collection<Event> events) {
        return events.stream().map(this::toEventShortDto).collect(Collectors.toList());
    }

    public Collection<EventFullDto> toEventFullDtoList(Collection<Event> events) {
        return events.stream().map(this::toEventFullDto).collect(Collectors.toList());
    }

    public Collection<EventFullWithCommentsDto> toEventFullWithCommentsDtoList(Collection<Event> events) {
        return events.stream().map(this::toEventFullWithCommentsDto).collect(Collectors.toList());
    }

    public Collection<EventShortWithCommentsDto> toEventShortWithCommentsDtoList(Collection<Event> events) {
        return events.stream().map(this::toEventShortWithCommentsDto).collect(Collectors.toList());
    }

    LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

    Location toLocation(LocationDto locationDto) {
        return Location.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }

    public NewEventRequest toNewEventRequest(NewEventDto dto) {
        return NewEventRequest.builder()
                .annotation(dto.getAnnotation())
                .categoryId(dto.getCategory())
                .description(dto.getDescription())
                .eventDate(dto.getEventDate() == null ?
                        null : dto.getEventDate())
                .location(dto.getLocation() == null ?
                        null : toLocation(dto.getLocation()))
                .paid(dto.isPaid())
                .participantLimit(dto.getParticipantLimit())
                .requestModeration(dto.isRequestModeration())
                .title(dto.getTitle())
                .build();
    }

    public UpdateEventRequest toUpdateEventRequest(UpdateEventRequestDto updateRequest) {

        return UpdateEventRequest.builder()
                .annotation(updateRequest.getAnnotation())
                .categoryId(updateRequest.getCategoryId())
                .description(updateRequest.getDescription())
                .eventDate(updateRequest.getEventDate())
                .location(updateRequest.getLocation() == null ?
                        null : toLocation(updateRequest.getLocation()))
                .paid(updateRequest.getPaid())
                .participantLimit(updateRequest.getParticipantLimit())
                .eventState(updateRequest.getStateAction() == null ?
                        null : toEventState(updateRequest.getStateAction()))
                .title(updateRequest.getTitle())
                .build();
    }

    public UpdateEventRequest toUpdateEventRequest(UpdateEventAdminRequestDto updateRequest) {

        return UpdateEventRequest.builder()
                .annotation(updateRequest.getAnnotation())
                .categoryId(updateRequest.getCategoryId())
                .description(updateRequest.getDescription())
                .eventDate(updateRequest.getEventDate())
                .location(updateRequest.getLocation() == null ?
                        null : toLocation(updateRequest.getLocation()))
                .paid(updateRequest.getPaid())
                .participantLimit(updateRequest.getParticipantLimit())
                .eventState(updateRequest.getStateAction() == null ?
                        null : toEventState(updateRequest.getStateAction()))
                .title(updateRequest.getTitle())
                .comment(updateRequest.getComment() == null ?
                        null : commentMapper.toAdminEventComment(updateRequest.getComment()))
                .build();
    }

    EventState toEventState(StateAction stateAction) {
        if (PUBLISH_EVENT.equals(stateAction)) {
            return EventState.PUBLISHED;
        } else if (SEND_TO_REVIEW.equals(stateAction)) {
            return EventState.PENDING;
        } else if (CANCEL_REVIEW.equals(stateAction)) {
            return EventState.CANCELED;
        } else if (REJECT_EVENT.equals(stateAction)) {
            return EventState.REJECTED;
        }
        return null;
    }

    public EventRequestStatusUpdateRequest toEventRequestStatusUpdateRequests(EventRequestStatusUpdateRequestDto requestDto) {
        return EventRequestStatusUpdateRequest.builder()
                .requestIds(requestDto.getRequestIds())
                .status(RequestStatus.valueOf(requestDto.getStatus()))
                .build();
    }

    public Event partialEventUpdate(Event event, UpdateEventRequest updateRequest) {
        Event updatedEvent = event;
        if (updateRequest.getAnnotation() != null && !updateRequest.getAnnotation().isBlank()) {
            updatedEvent = updatedEvent.withAnnotation(updateRequest.getAnnotation());
        }
        if (updateRequest.getDescription() != null && !updateRequest.getDescription().isBlank()) {
            updatedEvent = updatedEvent.withDescription(updateRequest.getDescription());
        }
        if (updateRequest.getEventDate() != null) {
            updatedEvent = updatedEvent.withEventDate(updateRequest.getEventDate());
        }
        if (updateRequest.getLocation() != null) {
            updatedEvent = updatedEvent.withLocation(updateRequest.getLocation());
        }
        if (updateRequest.getPaid() != null) {
            updatedEvent = updatedEvent.withPaid(updateRequest.getPaid());
        }
        if (updateRequest.getParticipantLimit() != null) {
            updatedEvent = updatedEvent.withParticipantLimit(updateRequest.getParticipantLimit());
        }
        if (updateRequest.getRequestModeration() != null) {
            updatedEvent = updatedEvent.withModeration(updateRequest.getRequestModeration());
        }
        if (updateRequest.getEventState() != null) {
            if (updateRequest.getEventState() == EventState.PUBLISHED) {
                updatedEvent = updatedEvent.withPublishedDate(LocalDateTime.now(clock));
            }
            updatedEvent = updatedEvent.withState(updateRequest.getEventState());
        }
        if (updateRequest.getTitle() != null && !updateRequest.getTitle().isBlank()) {
            updatedEvent = updatedEvent.withTitle(updateRequest.getTitle());
        }
        return updatedEvent;
    }

    public Event partialEventUpdate(Event event, Category category, UpdateEventRequest updateRequest) {
        Event updatedEvent = partialEventUpdate(event, updateRequest);

        if (category != null) {
            updatedEvent = updatedEvent.withCategory(category);
        }
        return updatedEvent;
    }

    public Event toEvent(User user, Category category, NewEventRequest eventRequest) {
        if (eventRequest.getEventDate().isBefore(LocalDateTime.now(clock).plusHours(2))) {
            throw new EwmIllegalArgumentException("Event must not be earlier than 2 hours");
        }

        return Event.builder()
                .annotation(eventRequest.getAnnotation())
                .category(category)
                .confirmedRequests(0)
                .createdDate(LocalDateTime.now(clock))
                .description(eventRequest.getDescription())
                .eventDate(eventRequest.getEventDate())
                .initiator(user)
                .location(eventRequest.getLocation())
                .paid(eventRequest.getPaid())
                .participantLimit(eventRequest.getParticipantLimit())
                .moderation(eventRequest.getRequestModeration())
                .state(EventState.PENDING)
                .title(eventRequest.getTitle())
                .views(0).build();
    }

    public EventSearchFilter toEventSearchPublicFilter(PublicEventSearchFilter publicEventSearchFilter) {
        return EventSearchFilter.builder()
                .text(publicEventSearchFilter.getText())
                .users(publicEventSearchFilter.getUsers())
                .states(new String[]{"PUBLISHED"})
                .categories(publicEventSearchFilter.getCategories())
                .rangeStart(publicEventSearchFilter.getRangeStart())
                .rangeEnd(publicEventSearchFilter.getRangeEnd())
                .paid(publicEventSearchFilter.getPaid())
                .sort(publicEventSearchFilter.getSort())
                .onlyAvailable(publicEventSearchFilter.getOnlyAvailable())
                .from(publicEventSearchFilter.getFrom())
                .size(publicEventSearchFilter.getSize())
                .build();
    }
}
