package ru.practicum.main.controller.publicapi

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.practicum.main.dto.event.EventSearchFilter
import ru.practicum.main.dto.event.PublicEventSearchFilter
import ru.practicum.main.exception.EwmNotFoundException
import ru.practicum.main.handler.MainServiceHandler
import ru.practicum.main.mapper.event.EventMapper
import ru.practicum.main.service.event.EventService
import spock.lang.Specification


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PublicEventControllerSpec extends Specification {

    def "Should return 200 when get all events"() {
        given:
        def service = Mock(EventService)
        def eventMapper = Stub(EventMapper) {
            toEventSearchPublicFilter(_ as PublicEventSearchFilter) >> EventSearchFilter.builder().build()
        }
        def controller = new PublicEventController(service, eventMapper)
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        when:
        def request = get("/events")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        and:
        server.perform(request)
                .andExpect(status().isOk())
        then:
        interaction {
            1 * service.getAllEvents(EventSearchFilter.builder().build(), "/events", _ as String)
        }
    }

    def "Should return 404 when event not found"() {
        given:
        def service = Mock(EventService)
        def controller = new PublicEventController(service, Mock(EventMapper))
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        when:
        def request = get("/events/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        and:
        server.perform(request)
                .andExpect(status().isNotFound())
        then:
        interaction {
            1 * service.getEventByIdPublic(1L, "/events/1", _ as String) >>
                    {
                        throw new EwmNotFoundException("")
                    }
        }
    }
}
