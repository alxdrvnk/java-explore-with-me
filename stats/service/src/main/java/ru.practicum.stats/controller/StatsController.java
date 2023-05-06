package ru.practicum.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> create(@RequestBody EndpointHitDto dto) {
        log.info("Add EndpointHit with data: " + dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(@RequestParam String start,
                                       @RequestParam String end,
                                       @RequestParam(required = false) List<String> uris,
                                       @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Get stats with values: start = {} end = {} uris = {} unique = {}", start, end, uris, unique);
        return statsMapper.toListViewStatsDto(
                statsService.getStats(
                        dateTimeConverter.parseDate(start),
                        dateTimeConverter.parseDate(end), uris, unique));
    }
}
