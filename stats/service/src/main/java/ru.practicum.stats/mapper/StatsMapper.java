package ru.practicum.stats.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.stats.converter.DateTimeConverter;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.ViewStats;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class StatsMapper {

    private final DateTimeConverter converter;

    public EndpointHit toEndpointHit(EndpointHitDto dto) {
        return EndpointHit.builder()
                .id(dto.getId())
                .app(dto.getApp())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .timestamp(
                        converter.parseDate(dto.getTimestamp()))
                .build();
    }

    public ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return ViewStatsDto.builder()
                .app(viewStats.getApp())
                .uri(viewStats.getUri())
                .hits(viewStats.getHits()).build();
    }

    public List<ViewStatsDto> toListViewStatsDto(List<ViewStats> viewStatsList) {
        return viewStatsList.stream().map(this::toViewStatsDto).collect(Collectors.toList());
    }
}
