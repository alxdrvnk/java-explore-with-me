package ru.practicum.main.mapper.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.main.converter.DateTimeConverter;
import ru.practicum.main.dto.event.*;
import ru.practicum.main.mapper.category.CategoryMapper;
import ru.practicum.main.mapper.user.UserMapper;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.model.event.EventState;
import ru.practicum.main.model.event.Location;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    private final DateTimeConverter dateTimeConverter;

    public EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(dateTimeConverter.formatDate(event.getCreatedDate()))
                .description(event.getDescription())
                .eventDate(dateTimeConverter.formatDate(event.getEventDate()))
                .initiator(userMapper.toUserShortDto(event.getInitiator()))
                .location(this.toLocationDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(dateTimeConverter.formatDate(event.getPublishedDate()))
                .requestModeration(event.getModeration())
                .state(event.getState().name())
                .title(event.getTitle())
                .views(event.getViews())
                .build();

    }
    public EventShortDto toEventShortDto(Event event)  {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(dateTimeConverter.formatDate(event.getEventDate()))
                .initiator(userMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }
    public Collection<EventShortDto> toEventShortDtoList(Collection<Event> events) {
        return events.stream().map(this::toEventShortDto).collect(Collectors.toList());
    }


    public Collection<EventFullDto> toEventFullDtoList(Collection<Event> events) {
        return events.stream().map(this::toEventFullDto).collect(Collectors.toList());
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

    public Event toEvent(NewEventDto dto) {
        return Event.builder()
                .annotation(dto.getAnnotation())
                .category(Category.builder().id(dto.getCategory()).build())
                .description(dto.getDescription())
                .eventDate(dateTimeConverter.parseDate(dto.getEventDate()))
                .location(this.toLocation(dto.getLocation()))
                .paid(dto.getPaid())
                .participantLimit(dto.getParticipantLimit())
                .moderation(dto.getRequestModeration())
                .title(dto.getTitle())
                .build();

    }

    public Event toEvent(UpdateEventUserRequestDto requestDto) {
        return Event.builder()
                .annotation(requestDto.getAnnotation())
                .category(Category.builder().id(requestDto.getCategoryId()).build())
                .description(requestDto.getDescription())
                .eventDate(dateTimeConverter.parseDate(requestDto.getEventDate()))
                .location(this.toLocation(requestDto.getLocation()))
                .paid(requestDto.getPaid())
                .participantLimit(requestDto.getParticipantLimit())
                .moderation(requestDto.getRequestModeration())
                .state(toEventState(requestDto.getStateAction()))
                .title(requestDto.getTitle())
                .build();
    }

    EventState toEventState(StateAction stateAction) {
        switch (stateAction){
            case PUBLISH_EVENT:
                return EventState.PUBLISHED;
            case SEND_TO_REVIEW:
                return EventState.PENDING;
            case CANCEL_REVIEW:
                return EventState.CANCELED;
            default:
                return null;
        }
    }
}
