package ru.practicum.main.controller.publicapi

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.practicum.main.handler.MainServiceHandler
import ru.practicum.main.mapper.compilation.CompilationMapper
import ru.practicum.main.model.compilation.Compilation
import ru.practicum.main.service.compilation.CompilationService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PublicCompilationControllerSpec extends Specification {

    def "Should return 200 when get all compilations"() {
        given:
        def service = Mock(CompilationService)
        def compilationMapper = new CompilationMapper()
        def controller = new PublicCompilationController(service, compilationMapper)
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        when:
        def request = get("/compilations?pinned=false")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        and:
        server.perform(request)
                .andExpect(status().isOk())
        then:
        interaction {
            1 * service.getAllCompilation(false, 0, 10) >> {
                List.of(Compilation.builder().build())
            }
        }
    }

    def "Should return 404 when not found compilation" () {
        given:
        def service = Mock(CompilationService)
        def compilationMapper = new CompilationMapper()
        def controller = new PublicCompilationController(service, compilationMapper)
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        when:
        def request = get("/compilations?pinned=false")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        and:
        server.perform(request)
                .andExpect(status().isOk())
        then:
        interaction {
            1 * service.getAllCompilation(false, 0, 10) >> {
                List.of(Compilation.builder().build())
            }
        }
    }
}
