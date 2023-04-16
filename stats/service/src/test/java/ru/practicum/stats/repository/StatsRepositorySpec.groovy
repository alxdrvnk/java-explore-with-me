package ru.practicum.stats.repository

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import ru.practicum.stats.configuration.DbUnitConfig
import ru.practicum.stats.model.EndpointHit
import spock.lang.Specification

import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase
@ContextConfiguration(classes = [DbUnitConfig.class])
@TestExecutionListeners([TransactionDbUnitTestExecutionListener, DependencyInjectionTestExecutionListener])
class StatsRepositorySpec extends Specification {

    @Autowired
    private StatsRepository repository

    def "Should create EventHit with generated id"() {
        given:
        EndpointHit endpointHit = EndpointHit.builder()
                .app("testapp")
                .uri("test_uri")
                .ip("192.168.100.100")
                .timestamp(LocalDateTime.of(2007, 9, 1, 12, 0, 0))
                .build();
        when:
        def dbEndpointHit = repository.save(endpointHit)
        then:
        dbEndpointHit.getId() > 0
        dbEndpointHit.getApp() == endpointHit.getApp()
        dbEndpointHit.getIp() == endpointHit.getIp()
        dbEndpointHit.getTimestamp() == LocalDateTime.of(2007, 9, 1, 12, 0, 0)
    }

    @DatabaseSetup(value = "classpath:database/set_event_hit.xml", connection = "dbUnitDatabaseConnection")
    def "Should return ViewStats when get with unique flag"() {
        when:
        def listViewStats = repository.findByDateAndUnique(
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                LocalDateTime.of(2007, 9, 10, 12, 0, 0),
                List.of("testuri"))
        then:
        listViewStats.size() == 1
        with(listViewStats) {
            app == ["test app"]
            uri == ["testuri"]
            hits == [2]
        }
    }

    @DatabaseSetup(value = "classpath:database/set_event_hit.xml", connection = "dbUnitDatabaseConnection")
    def "Should return ViewStats when get without unique flag"() {
        when:
        def listViewStats = repository.findByDateAndNotUnique(
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                LocalDateTime.of(2007, 9, 10, 12, 0, 0),
                List.of("testuri"))
        then:
        listViewStats.size() == 1
        with(listViewStats) {
            app == ["test app"]
            uri == ["testuri"]
            hits == [3]
        }
    }

    @DatabaseSetup(value = "classpath:database/set_event_hit.xml", connection = "dbUnitDatabaseConnection")
    def "Should return ViewStats when get without uris and with unique flag"() {
        when:
        def listViewStats = repository.findByDateAndUniqueWithoutUris(
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                LocalDateTime.of(2007, 9, 10, 12, 0, 0))
        then:
        listViewStats.size() == 2
        with(listViewStats) {
            app == ["test app", "test another app"]
            uri == ["testuri", "anotheruri"]
            hits == [2, 1]
        }
    }

    @DatabaseSetup(value = "classpath:database/set_event_hit.xml", connection = "dbUnitDatabaseConnection")
    def "Should return ViewStats when get without uris and unique flag"() {
        when:
        def listViewStats = repository.findByDateAndNotUniqueWithoutUris(
                LocalDateTime.of(2007, 9, 1, 12, 0, 0),
                LocalDateTime.of(2007, 9, 10, 12, 0, 0))
        then:
        listViewStats.size() == 2
        with(listViewStats) {
            app == ["test app", "test another app"]
            uri == ["testuri", "anotheruri"]
            hits == [3, 2]
        }
    }
}
