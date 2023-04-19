package ru.practicum.stats.service;

import org.springframework.stereotype.Service;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface StatsService {

    void create(EndpointHit endpointHit);

    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uri, Boolean unique);
}
