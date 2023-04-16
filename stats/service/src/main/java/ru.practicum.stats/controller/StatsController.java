package ru.practicum.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.converter.DateTimeConverter;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.mapper.StatsMapper;
import ru.practicum.stats.service.StatsService;

import java.util.List;

@Slf4j(topic = "Stats Controller")
@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;
    private final StatsMapper statsMapper;
    private final DateTimeConverter dateTimeConverter;

    @PostMapping("/hit")
    public void create(@RequestBody EndpointHitDto dto) {
        statsService.create(statsMapper.toEndpointHit(dto));
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(@RequestParam String start,
                                       @RequestParam String end,
                                       @RequestParam List<String> uris,
                                       @RequestParam(defaultValue = "false") Boolean unique) {

        return statsMapper.toListViewStatsDto(
                statsService.getStats(
                        dateTimeConverter.parseDate(start),
                        dateTimeConverter.parseDate(end), uris, unique));
    }
}
