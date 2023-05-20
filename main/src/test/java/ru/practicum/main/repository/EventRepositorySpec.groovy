package ru.practicum.main.repository

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import ru.practicum.main.configuration.DbUnitConfig
import ru.practicum.main.model.category.Category
import ru.practicum.main.model.event.Event
import ru.practicum.main.model.event.EventState
import ru.practicum.main.model.event.Location
import ru.practicum.main.model.user.User
import spock.lang.Specification

import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase
@ContextConfiguration(classes = [DbUnitConfig.class])
@TestExecutionListeners([TransactionDbUnitTestExecutionListener, DependencyInjectionTestExecutionListener])
class EventRepositorySpec extends Specification {

    @Autowired
    private EventRepository repository

    @DatabaseSetup(value = "classpath:database/event/set_initiator_category.xml",
            connection = "dbUnitDatabaseConnection")
    def "Should return event with generate id"() {
        given:
        def initiator = User.builder()
                .id(1L)
                .name("Initiator")
                .email("initiator@mail.mail")
                .build()

        def category = Category.builder()
                .id(1L)
                .name("Test event category")
                .build()

        def location = Location.builder()
                .lat(0)
                .lon(0)
                .build()

        def event = Event.builder()
                .id(1L)
                .annotation("Test event annotation")
                .category(category)
                .confirmedRequests(0)
                .createdDate(LocalDateTime.of(2000, 1, 1, 12, 0, 0))
                .description("Test event description")
                .eventDate(
                        LocalDateTime.of(2000, 1, 1, 12, 0, 0)
                                .plusDays(3))
                .initiator(initiator)
                .location(location)
                .paid(false)
                .participantLimit(0)
                .moderation(false)
                .state(EventState.PENDING)
                .title("Test event title")
                .build()

        when:
        def dbEvent = repository.save(event)

        then:
        dbEvent.getId() == 1
    }

    @DatabaseSetup(value = "classpath:database/event/set_event.xml",
            connection = "dbUnitDatabaseConnection")
    def "Should return event when get by id"() {
        when:
        def dbEvent = repository.findById(1L).get()

        then:
        dbEvent.getId() == 1
        dbEvent.getAnnotation() == "Test event annotation"
        dbEvent.getCategory().getId() == 1
        dbEvent.getCategory().getName() == "Test event category"
        dbEvent.getConfirmedRequests() == 0
        dbEvent.getCreatedDate() == LocalDateTime.of(2000, 1, 1, 12, 0, 0)
        dbEvent.getDescription() == "Test event description"
        dbEvent.getEventDate() == LocalDateTime.of(2000, 1, 4, 12, 0, 0)
        dbEvent.getInitiator().getId() == 1
        dbEvent.getInitiator().getName() == "Initiator"
        dbEvent.getState() == EventState.PENDING
    }

    @DatabaseSetup(value = "classpath:database/event/set_pending_event.xml",
            connection = "dbUnitDatabaseConnection")
    def "Should return event with pending state"() {
        when:
        def dbEvents = repository.findAllByState(EventState.PENDING)

        then:
        with(dbEvents) {
            id == [2]
            state == [EventState.PENDING]
        }
    }
}
