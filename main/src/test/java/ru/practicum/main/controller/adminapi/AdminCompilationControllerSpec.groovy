package ru.practicum.main.controller.adminapi

import groovy.json.JsonBuilder
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.practicum.main.dto.compilation.NewCompilationDto
import ru.practicum.main.dto.compilation.UpdateCompilationRequestDto
import ru.practicum.main.exception.EwmNotFoundException
import ru.practicum.main.handler.MainServiceHandler
import ru.practicum.main.mapper.compilation.CompilationMapper
import ru.practicum.main.model.compilation.Compilation
import ru.practicum.main.model.event.Event
import ru.practicum.main.service.compilation.CompilationService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AdminCompilationControllerSpec extends Specification {

    private final compilationMapper = new CompilationMapper()

    def "Should return 201 when create compilation"() {
        given:
        def service = Mock(CompilationService)
        def controller = new AdminCompilationController(service, compilationMapper)
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()
        and:
        def newCompilationDto = NewCompilationDto.builder()
                .events(List.of(1L, 2L))
                .pinned(false)
                .title("test")
                .build()
        when:
        def request = post("/admin/compilations")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new JsonBuilder(newCompilationDto).toString())
        and:
        server.perform(request)
                .andExpect(status().isCreated())
        then:
        interaction {
            1 * service.createCompilation(_ as Compilation) >> {
                Compilation.builder()
                        .id(1L)
                        .pinned(false)
                        .title("test")
                        .events(Set.of(Event.builder().build()))
                        .build()
            }
        }
    }

    def "Should return 400 when request is incorrect"() {
        given:
        def service = Mock(CompilationService)
        def controller = new AdminCompilationController(service, compilationMapper)
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()
        and:
        def newCompilationDto = NewCompilationDto.builder()
                .events(List.of(1L, 2L))
                .pinned(false)
                .build()
        expect:
        def request = post("/admin/compilations")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new JsonBuilder(newCompilationDto).toString())
        and:
        server.perform(request)
                .andExpect(status().isBadRequest())
    }

    def "Should return 404 when delete unexpected compilation"() {
        given:
        def service = Mock(CompilationService)
        def controller = new AdminCompilationController(service, compilationMapper)
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        when:
        def request = delete("/admin/compilations/9999")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        and:
        server.perform(request)
                .andExpect(status().isNotFound())
        then:
        interaction {
            1 * service.deleteCompilation(9999L) >> { throw new EwmNotFoundException("") }
        }
    }

    def "Should return 200 when update compilation"() {
        given:
        def service = Mock(CompilationService)
        def controller = new AdminCompilationController(service, compilationMapper)
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        and:
        def updateCompilationRequest = UpdateCompilationRequestDto.builder()
                .events(List.of(1L, 2L))
                .pinned(false)
                .title("test")
                .build()

        when:
        def request = patch("/admin/compilations/9999")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new JsonBuilder(updateCompilationRequest).toString())
        and:
        server.perform(request)
                .andExpect(status().isOk())
        then:
        interaction {
            1 * service.updateCompilation(9999L, _ as Compilation) >> { Compilation.builder().build()}
        }
    }

    def "Should return 404 when update unexpected compilation"() {
        given:
        def service = Mock(CompilationService)
        def controller = new AdminCompilationController(service, compilationMapper)
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        and:
        def updateCompilationRequest = UpdateCompilationRequestDto.builder()
                .events(List.of(1L, 2L))
                .pinned(false)
                .title("test")
                .build()

        when:
        def request = patch("/admin/compilations/9999")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new JsonBuilder(updateCompilationRequest).toString())
        and:
        server.perform(request)
                .andExpect(status().isNotFound())
        then:
        interaction {
            1 * service.updateCompilation(9999L, _ as Compilation) >> { throw new EwmNotFoundException("") }
        }
    }
}
