package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query(value = "SELECT new ru.practicum.stats.model.ViewStats(app, uri, COUNT(DISTINCT e.ip)) FROM EndpointHit AS e " +
            "WHERE e.uri in :uris " +
            "AND e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e.ip) DESC")
    List<ViewStats> findByDateAndUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT new ru.practicum.stats.model.ViewStats(app, uri, COUNT(e.ip)) FROM EndpointHit AS e " +
            "WHERE e.uri in :uris " +
            "AND e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e.ip) DESC")
    List<ViewStats> findByDateAndNotUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT new ru.practicum.stats.model.ViewStats(app, uri, COUNT(DISTINCT e.ip)) FROM EndpointHit AS e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e.ip) DESC")
    List<ViewStats> findByDateAndUniqueWithoutUris(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT new ru.practicum.stats.model.ViewStats(app, uri, COUNT(e.ip)) FROM EndpointHit AS e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e.ip) DESC")
    List<ViewStats> findByDateAndNotUniqueWithoutUris(LocalDateTime start, LocalDateTime end);
}
