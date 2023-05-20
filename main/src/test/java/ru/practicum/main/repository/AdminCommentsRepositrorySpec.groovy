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
import ru.practicum.main.model.event.Event
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase
@ContextConfiguration(classes = [DbUnitConfig.class])
@TestExecutionListeners([TransactionDbUnitTestExecutionListener, DependencyInjectionTestExecutionListener])
class AdminCommentsRepositorySpec extends Specification {

    @Autowired
    private AdminCommentsRepository adminCommentsRepository


    @DatabaseSetup(value = "classpath:database/comments/set_comments.xml",
            connection = "dbUnitDatabaseConnection")
    def "Should return all comments by events id list"() {
        when:
        def dbComments = adminCommentsRepository.findAllByEventIdInAndCorrected(List.of(1L, 2L), false)
        then:
        with(dbComments) {
            id == [1, 2, 3, 4]
        }
    }

    @DatabaseSetup(value = "classpath:database/comments/set_comments.xml",
            connection = "dbUnitDatabaseConnection")
    def "Should return all comments in desc order for event"() {
        given:
        def event = Event.builder()
                .id(1L)
                .build()
        when:
        def dbComments = adminCommentsRepository.findAllByEventAndCorrectedOrderByCreatedDateDesc(event, false)
        then:
        with(dbComments) {
            id == [2, 1]
        }
    }
}
