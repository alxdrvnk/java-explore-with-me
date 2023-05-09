package ru.practicum.main.controller.adminapi

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.practicum.main.dto.event.EventSearchFilter
import ru.practicum.main.dto.event.LocationDto
import ru.practicum.main.dto.event.StateAction
import ru.practicum.main.dto.event.UpdateEventAdminRequestDto
import ru.practicum.main.exception.EwmNotFoundException
import ru.practicum.main.handler.MainServiceHandler
import ru.practicum.main.mapper.category.CategoryMapper
import ru.practicum.main.mapper.event.EventMapper
import ru.practicum.main.mapper.user.UserMapper
import ru.practicum.main.model.event.UpdateEventRequest
import ru.practicum.main.service.event.EventService
import spock.lang.Specification

import java.time.Clock
import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AdminEventControllerSpec extends Specification {

    def "Should return 400 when get incorrect request"() {
        given:
        def service = Mock(EventService)
        def controller = new AdminEventController(service, Mock(EventMapper))
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        expect:
        def request = get("/admin/events?rangeStart=2000")
                .contentType(MediaType.APPLICATION_JSON_VALUE)

        server.perform(request)
                .andExpect(status().isBadRequest())
    }

    def "Should return 200 when get events"() {
        given:
        def service = Mock(EventService)
        def controller = new AdminEventController(service, Mock(EventMapper))
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        when:
        def request = get("/admin/events?rangeStart=2007-09-01 12:00:00")
                .contentType(MediaType.APPLICATION_JSON_VALUE)

        server.perform(request)
                .andExpect(status().isOk())

        then:
        interaction {
            1 * service.getAllEvents(EventSearchFilter.builder()
                    .rangeStart(LocalDateTime.of(2007,9,1,12,0,0))
                    .from(0)
                    .size(10)
                    .build())
        }
    }


    def "Should return 404 when update unexpected event"() {
        given:
        def service = Mock(EventService)
        def eventMapper = new EventMapper(new CategoryMapper(), new UserMapper(), Clock.systemUTC())
        def controller = new AdminEventController(service, eventMapper)
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        and:
        def updateRequest = UpdateEventAdminRequestDto.builder()
                .annotation("a" * 25)
                .categoryId(1L)
                .description("a" * 25)
                .eventDate(LocalDateTime.of(2000,1,1,12,0,0))
                .location(LocationDto.builder().lat(20.0f).lon(20.0f).build())
                .paid(true)
                .participantLimit(0)
                .stateAction(StateAction.PUBLISH_EVENT)
                .title("test")
                .build()

        when:
        def request = patch("/admin/events/9999")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(updateRequest))

        server.perform(request)
                .andExpect(status().isNotFound())

        then:
        interaction {
            1 * service.updateEventByAdmin(_ as Long, _ as UpdateEventRequest) >>
                    { throw new EwmNotFoundException("") }
        }
    }
}
