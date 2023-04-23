package ru.practicum.main.service.admin

import ru.practicum.main.exception.EwmAlreadyExistsException
import ru.practicum.main.exception.EwmNotFoundException
import ru.practicum.main.model.User
import ru.practicum.main.repository.UserRepository
import spock.lang.Specification


class AdminUserServiceImplSpec extends Specification {
    def "Should throw EwemAlreadyExistsException when create User with existing email"() {
        given:
        User user = User.builder()
                .email("test@mail.mail")
                .name("test")
                .build()

        def repository = Stub(UserRepository) {
            findByEmail("test@mail.mail") >> { Optional.of(User.builder().build()) }
        }

        def service = new AdminUserServiceImpl(repository)

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

        def service = new AdminUserServiceImpl(repository)

        when:
        service.getUserById(1L)

        then:
        thrown(EwmNotFoundException)
    }
}
