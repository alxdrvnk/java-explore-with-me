package ru.practicum.main.mapper.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.main.dto.event.EventFullDto;
import ru.practicum.main.dto.event.LocationDto;
import ru.practicum.main.mapper.category.CategoryMapper;
import ru.practicum.main.mapper.user.UserMapper;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.model.event.Location;

import java.lang.module.FindException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    //TODO:Create Localdatetime formater
    public EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedDate().toString())
                .description(event.getDescription())
                .eventDate(event.getEventDate().toString())
                .initiator(userMapper.toUserShortDto(event.getInitiator()))
                .location(this.toLocationDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedDate().toString())
                .requestModeration(event.getModeration())
                .state(event.getState().name())
                .title(event.getTitle())
                .views(event.getViews())
                .build();

    }

    public List<EventFullDto> toEventFullDtoList(Collection<Event> events) {
        return events.stream().map(this::toEventFullDto).collect(Collectors.toList());
    }

    LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}
