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
import ru.practicum.main.model.request.ParticipationRequest
import ru.practicum.main.model.request.RequestStatus
import ru.practicum.main.model.user.User
import spock.lang.Specification

import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase
@ContextConfiguration(classes = [DbUnitConfig.class])
@TestExecutionListeners([TransactionDbUnitTestExecutionListener, DependencyInjectionTestExecutionListener])
class RequestRepositorySpec extends Specification {
    @Autowired
    private RequestRepository repository

    @DatabaseSetup(value = "classpath:database/request/set_event_initiator_requester_category.xml",
            connection = "dbUnitDatabaseConnection")
    def "Should return request with generate id"() {
        given:
        def initiator = User.builder()
                .id(1L)
                .name("Initiator")
                .email("initiator@mail.mail")
                .build()

        def requester = User.builder()
                .id(2L)
                .email("requester@mail.mail")
                .name("Requester")
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

        def participationRequest = ParticipationRequest.builder()
                .event(event)
                .requester(requester)
                .createdDate(LocalDateTime.of(2000, 1, 1, 12, 0, 0))
                .status(RequestStatus.PENDING)
                .build()

        when:
        def dbRequest = repository.save(participationRequest)

        then:
        dbRequest.getId() == 1
    }

    @DatabaseSetup(value = "classpath:database/request/set_request.xml",
            connection = "dbUnitDatabaseConnection")
    def "Should return request when get by requester"() {
        when:
        def dbRequest = repository.findById(1L).get()

        then:
        dbRequest.getId() == 1L
        with(dbRequest.getRequester()) {
            id == 2
            name == "Requester"
            email == "requester@mail.mail"
        }
        with(dbRequest.getEvent()) {
            id == 1
            annotation == "Test event annotation"
            category.getId() == 1
            category.getName() == "Test event category"
            confirmedRequests == 0
            createdDate == LocalDateTime.of(2000,1,1,12,0,0)
            description == "Test event description"
            eventDate == LocalDateTime.of(2000,1,4,12,0,0)
            initiator.getId() == 1
            initiator.getName() == "Initiator"
            state == EventState.PENDING
        }
        dbRequest.status == RequestStatus.PENDING
    }
}
