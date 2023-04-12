package ru.practicum.stats.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.repository.StatsRepository;
import ru.practicum.stats.service.StatsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    @Override
    public void create(EndpointHit endpointHit) {
        statsRepository.save(endpointHit);
    }

    @Override
    public List<ViewStatsDto> getStats(String start, String end, List<String> uri, Boolean unique) {
        return null;
    }
}
