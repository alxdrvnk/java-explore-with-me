package ru.practicum.main.service.user

import org.springframework.dao.DataIntegrityViolationException
import ru.practicum.main.exception.EwmAlreadyExistsException
import ru.practicum.main.exception.EwmNotFoundException
import ru.practicum.main.model.user.User
import ru.practicum.main.repository.UserRepository
import spock.lang.Specification

class UserServiceImplSpec extends Specification {
    def "Should throw EwmAlreadyExistsException when create User with existing email"() {
        given:
        User user = User.builder()
                .email("test@mail.mail")
                .name("test")
                .build()

        def repository = Stub(UserRepository) {
            save(user) >> { throw new DataIntegrityViolationException("") }
        }

        def service = new UserServiceImpl(repository)

        when:
        service.createUser(user)

        then:
        thrown(EwmAlreadyExistsException)
    }

    def "Should throw EwmNotFoundException when User not found by Id"() {
        given:
        def repository = Stub(UserRepository) {
            findById(1L) >> { Optional.empty() }
        }

        def service = new UserServiceImpl(repository)

        when:
        service.getUserById(1L)

        then:
        thrown(EwmNotFoundException)
    }
}
