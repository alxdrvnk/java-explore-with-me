package ru.practicum.stats.mapper;

import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.model.EndpointHit;

public class EndpointHitMapper {

    public EndpointHit toEndpointHit(EndpointHitDto dto) {
        return EndpointHit.builder()
                .id(dto.getId())
                .app(dto.getApp())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .timestamp(dto.getTimestamp())
                .build();
    }
}
