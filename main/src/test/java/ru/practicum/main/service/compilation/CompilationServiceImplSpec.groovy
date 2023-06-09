package ru.practicum.main.service.compilation

import ru.practicum.main.exception.EwmNotFoundException
import ru.practicum.main.mapper.compilation.CompilationMapper
import ru.practicum.main.repository.CompilationRepository
import ru.practicum.main.service.event.EventService
import spock.lang.Specification

class CompilationServiceImplSpec extends Specification {

    def "Should throw EwmNotFoundException when delete unexpected compilation" () {
        given:
        def repository = Stub(CompilationRepository) {
            existsById(1L) >> Optional.empty()
        }

        def service = new CompilationServiceImpl(repository, Mock(EventService), Mock(CompilationMapper))

        when:
        service.deleteCompilation(1L)
        then:
        thrown(EwmNotFoundException)
    }
}
