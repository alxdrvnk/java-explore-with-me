package ru.practicum.main.controller.privateapi


import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.practicum.main.exception.EwmNotFoundException
import ru.practicum.main.handler.MainServiceHandler
import ru.practicum.main.mapper.request.RequestMapper
import ru.practicum.main.service.request.ParticipationRequestService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PrivateRequestControllerSpec extends Specification {

    def "Should return 400 when get request is incorrect"() {
        given:
        def service = Mock(ParticipationRequestService)
        def controller = new PrivateRequestController(service, Mock(RequestMapper))
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        expect:
        def request = get("/users/abc/requests")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        and:
        server.perform(request)
                .andExpect(status().isBadRequest())
    }

    def "Should return 404 when user not found"() {
        given:
        def service = Mock(ParticipationRequestService)
        def controller = new PrivateRequestController(service, Mock(RequestMapper))
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        when:
        def request = get("/users/1/requests")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        and:
        server.perform(request)
                .andExpect(status().isNotFound())
        then:
        interaction {
            1 * service.getRequestsByUser(1L) >> { throw new EwmNotFoundException("") }
        }
    }

    def "Should return 404 when event not found"() {
        given:
        def service = Mock(ParticipationRequestService)
        def controller = new PrivateRequestController(service, Mock(RequestMapper))
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        when:
        def request = post("/users/1/requests?eventId=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)

        and:
        server.perform(request)
                .andExpect(status().isNotFound())
        then:
        interaction {
            1 * service.createRequest(1L, 1L) >> { throw new EwmNotFoundException("") }
        }
    }

    def "Should return 200 when create new request"() {
        given:
        def service = Mock(ParticipationRequestService)
        def controller = new PrivateRequestController(service, Mock(RequestMapper))
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        when:
        def request = post("/users/1/requests?eventId=1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)

        and:
        server.perform(request)
                .andExpect(status().isCreated())
        then:
        interaction {
            1 * service.createRequest(1L, 1L)
        }
    }
}
