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
import ru.practicum.main.model.compilation.Compilation
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
class CompilationRepositorySpec extends Specification {
    @Autowired
    private CompilationRepository repository

    @DatabaseSetup(value = "classpath:database/compilation/set_event_initiator_category.xml",
            connection = "dbUnitDatabaseConnection")
    def "Should return Compilation with generated id"() {
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
                .eventDate(LocalDateTime.of(2000, 1, 1, 12, 0, 0).plusDays(3))
                .initiator(initiator)
                .location(location)
                .paid(false)
                .participantLimit(0)
                .moderation(false)
                .state(EventState.PENDING)
                .title("Test event title")
                .build()

        def compilation = Compilation.builder()
                .pinned(false)
                .title("test")
                .events(Set.of(event))
                .build()
        when:
        def dbCompilation = repository.saveAndFlush(compilation)

        then:
        dbCompilation.getId() == 1
    }

    @DatabaseSetup(value = "classpath:database/compilation/set_compilation.xml",
            connection = "dbUnitDatabaseConnection")
    def "Should return compilation when get by id"() {
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
        def dbCompilation = repository.findById(1L).get()

        then:
        dbCompilation.getId() == 1
        with(dbCompilation.getEvents()) {
            id == [event.getId()]
            title == ["Test event title"]
        }
    }
}
