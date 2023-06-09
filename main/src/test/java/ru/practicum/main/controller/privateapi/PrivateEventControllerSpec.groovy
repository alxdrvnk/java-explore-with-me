package ru.practicum.main.controller.privateapi

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonBuilder
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.practicum.main.dto.event.EventRequestStatusUpdateRequestDto
import ru.practicum.main.dto.event.LocationDto
import ru.practicum.main.dto.event.NewEventDto
import ru.practicum.main.dto.event.UpdateEventUserRequestDto
import ru.practicum.main.exception.EwmNotFoundException
import ru.practicum.main.handler.MainServiceHandler
import ru.practicum.main.mapper.category.CategoryMapper
import ru.practicum.main.mapper.comment.CommentMapper
import ru.practicum.main.mapper.event.EventMapper
import ru.practicum.main.mapper.request.RequestMapper
import ru.practicum.main.mapper.user.UserMapper
import ru.practicum.main.model.category.Category
import ru.practicum.main.model.event.*
import ru.practicum.main.model.user.User
import ru.practicum.main.service.event.EventService
import spock.lang.Specification

import java.time.Clock
import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PrivateEventControllerSpec extends Specification {

    def "Should return 500 when request is incorrect"() {
        given:
        def service = Mock(EventService)
        def controller = new PrivateEventController(service, Mock(EventMapper), Mock(RequestMapper))
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        expect:
        def request = get("/users/abc/events")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        and:
        server.perform(request)
                .andExpect(status().isInternalServerError())
    }

    def "Should return 404 when event not found"() {
        given:
        def service = Mock(EventService)
        def controller = new PrivateEventController(service, Mock(EventMapper), Mock(RequestMapper))
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        when:
        def request = get("/users/1/events/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        and:
        server.perform(request)
                .andExpect(status().isNotFound())
        then:
        interaction {
            1 * service.getEventByIdPrivate(1L, 1L) >> { throw new EwmNotFoundException("") }
        }
    }

    def "Should return 201 when create new event"() {
        given:
        def service = Mock(EventService)
        def eventMapper = new EventMapper(new CategoryMapper(), new CommentMapper(), new UserMapper(), Clock.systemUTC())
        def controller = new PrivateEventController(service, eventMapper, Mock(RequestMapper))
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        and:
        def event = NewEventDto.builder()
                .annotation("a" * 20)
                .category(1L)
                .description("a" * 25)
                .eventDate(LocalDateTime.of(2000, 1, 1, 12, 0, 0))
                .location(LocationDto.builder().lat(0).lon(0).build())
                .paid(true)
                .participantLimit(0)
                .requestModeration(true)
                .title("test")
                .build()
        when:
        def request = post("/users/1/events")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(event))
        and:
        server.perform(request)
                .andExpect(status().isCreated())
        then:
        interaction {
            1 * service.createEvent(1L, _ as NewEventRequest) >> {
                Event.builder()
                        .category(Category.builder().build())
                        .location(Location.builder().lon(0).lat(0).build())
                        .eventDate(LocalDateTime.now())
                        .createdDate(LocalDateTime.now())
                        .publishedDate(LocalDateTime.now())
                        .initiator(User.builder().build())
                        .state(EventState.PUBLISHED)
                        .build()
            }
        }
    }

    def "Should return 200 when update events"() {
        given:
        def service = Mock(EventService)
        def eventMapper = new EventMapper(new CategoryMapper(), new CommentMapper(), new UserMapper(), Clock.systemUTC())
        def controller = new PrivateEventController(service, eventMapper, Mock(RequestMapper))
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        def event = UpdateEventUserRequestDto.builder()
                .annotation("a" * 20)
                .categoryId(1L)
                .description("a" * 25)
                .eventDate(LocalDateTime.of(2000, 1, 1, 12, 0, 0))
                .location(LocationDto.builder().lat(0).lon(0).build())
                .paid(true)
                .participantLimit(1)
                .requestModeration(true)
                .stateAction(UpdateEventUserRequestDto.StateAction.SEND_TO_REVIEW)
                .title("test")
                .build()
        when:
        def request = patch("/users/1/events/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(event))
        and:
        server.perform(request)
                .andExpect(status().isOk())
        then:
        interaction {
            1 * service.updateEventById(1L, 1L, _ as UpdateEventRequest) >> {
                Event.builder()
                        .category(Category.builder().id(3).name("Name").build())
                        .location(Location.builder().lat(0).lon(0).build())
                        .eventDate(LocalDateTime.now())
                        .participantLimit(2)
                        .createdDate(LocalDateTime.now())
                        .publishedDate(LocalDateTime.now())
                        .initiator(User.builder().build())
                        .state(EventState.PUBLISHED)
                        .comments(Collections.emptyList())
                        .build()
            }
        }
    }

    def "Should return 200 when update request status"() {
        given:
        def service = Mock(EventService)
        def eventMapper = new EventMapper(new CategoryMapper(), new CommentMapper(), new UserMapper(), Clock.systemUTC())
        def requestMapper = new RequestMapper()
        def controller = new PrivateEventController(service, eventMapper, requestMapper)
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        and:
        def eventRequestUpdate = EventRequestStatusUpdateRequestDto.builder()
                .requestIds(List.of(1L, 2L))
                .status("REJECTED")
                .build()
        when:
        def request = patch("/users/1/events/1/requests")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new JsonBuilder(eventRequestUpdate).toString())
        and:
        server.perform(request)
                .andExpect(status().isOk())
        then:
        interaction {
            1 * service.updateEventRequests(1L, 1L, _ as EventRequestStatusUpdateRequest) >> {
                EventRequestStatusUpdateResult.builder()
                        .confirmedRequests(Collections.emptyList())
                        .rejectedRequests(Collections.emptyList())
                        .build()
            }
        }
    }
}
