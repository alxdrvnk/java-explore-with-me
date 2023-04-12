package ru.practicum.stats.service;

import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.model.EndpointHit;

import java.util.List;

public interface StatsService {

    void create(EndpointHit endpointHit);

    List<ViewStatsDto> getStats(String start, String end, List<String> uri, Boolean unique);
}
