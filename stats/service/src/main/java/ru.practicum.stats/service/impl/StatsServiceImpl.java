package ru.practicum.stats.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.ViewStats;
import ru.practicum.stats.repository.StatsRepository;
import ru.practicum.stats.service.StatsService;

import java.time.LocalDateTime;
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
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uri, Boolean unique) {
        if (unique) {
            return CollectionUtils.isEmpty(uri) ?
                    statsRepository.findByDateAndUniqueWithoutUris(start, end) :
                    statsRepository.findByDateAndUnique(start, end, uri);
        } else {
            return CollectionUtils.isEmpty(uri) ?
                    statsRepository.findByDateAndNotUniqueWithoutUris(start, end) :
                    statsRepository.findByDateAndNotUnique(start, end, uri);
        }
    }
}
