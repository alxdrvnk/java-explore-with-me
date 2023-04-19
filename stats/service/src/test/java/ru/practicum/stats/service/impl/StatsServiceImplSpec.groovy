package ru.practicum.stats.service.impl


import ru.practicum.stats.repository.StatsRepository
import spock.lang.Specification

import java.time.LocalDateTime

class StatsServiceImplSpec extends Specification {

    def "Should return ViewStatsDto when get stats with unique flag and uri"() {
        given:
        def statsRepository = Mock(StatsRepository)
        def statsService = new StatsServiceImpl(statsRepository)

        when:
        statsService.getStats(
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                List.of("testuri"),
                true)

        then:
        1 * statsRepository.findByDateAndUnique(
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                List.of("testuri"))
    }

    def "Should return ViewStatsDto when get stats without uris and with unique flag"() {
        given:
        def statsRepository = Mock(StatsRepository)
        def statsService = new StatsServiceImpl(statsRepository)

        when:
        statsService.getStats(
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                null,
                true)

        then:
        1 * statsRepository.findByDateAndUniqueWithoutUris(
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                LocalDateTime.of(2007, 9, 1, 12, 0, 0))
    }

    def "Should return ViewStatsDto when get stats without unique flag and with uri"() {
        given:
        def statsRepository = Mock(StatsRepository)
        def statsService = new StatsServiceImpl(statsRepository)

        when:
        statsService.getStats(
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                List.of("testuri"),
                false)

        then:
        1 * statsRepository.findByDateAndNotUnique(
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                List.of("testuri")
        )
    }

    def "Should return ViewStatsDto when get stats without unique flag and uri"() {
        given:
        def statsRepository = Mock(StatsRepository)
        def statsService = new StatsServiceImpl(statsRepository)

        when:
        statsService.getStats(
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                null,
                false)

        then:
        1 * statsRepository.findByDateAndNotUniqueWithoutUris(
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                LocalDateTime.of(2007, 9, 1, 12, 0, 0))
    }
}
