package ru.practicum.main.repository

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import ru.practicum.main.configuration.DbUnitConfig
import ru.practicum.main.model.User
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase
@ContextConfiguration(classes = [DbUnitConfig.class])
@TestExecutionListeners([TransactionDbUnitTestExecutionListener, DependencyInjectionTestExecutionListener])
class UserRepositorySpec extends Specification {

    @Autowired
    private UserRepository repository

    def "Should create User with generated id"() {
        given:
        User user = User.builder()
                .email("test@mail.mail")
                .name("testName")
                .build()

        when:
        def dbUser = repository.save(user)

        then:
        dbUser.getId() == 1
    }

    @DatabaseSetup(value = "classpath:database/admin/users/set_users.xml", connection = "dbUnitDatabaseConnection")
    def "Should return list of Users if search by ids" () {
        when:
        def listUsers = repository.findAllById(List.of(1L,2L))
        then:
        listUsers.size() == 2
        with(listUsers) {
            id == [1,2]
            name == ["firstTestName", "secondTestName"]
            email == ["firstTest@mail.mail", "secondTest@mail.mail"]
        }
    }


    @DatabaseSetup(value = "classpath:database/admin/users/set_users.xml", connection = "dbUnitDatabaseConnection")
    def "Should return list of Users if search without ids" () {
        when:
        def listUsers = repository.findAll()
        then:
        listUsers.size() == 3
        with(listUsers) {
            id == [1,2,3]
            name == ["firstTestName", "secondTestName", "thirdTestName"]
            email == ["firstTest@mail.mail", "secondTest@mail.mail", "thirdTest@mail.mail"]
        }
    }


    @DatabaseSetup(value = "classpath:database/admin/users/set_users.xml", connection = "dbUnitDatabaseConnection")
    def "Should return list of Users if search with pageable params" () {
        when:
        def listUsers = repository.findAll(PageRequest.of(0,1)).getContent()
        then:
        listUsers.size() == 1
        with(listUsers) {
            id == [1]
            name == ["firstTestName"]
            email == ["firstTest@mail.mail"]
        }
    }
}
