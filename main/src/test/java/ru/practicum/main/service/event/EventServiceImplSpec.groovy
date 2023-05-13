package ru.practicum.main.service.event

import ru.practicum.main.converter.DateTimeConverter
import ru.practicum.main.exception.EwmIllegalArgumentException
import ru.practicum.main.mapper.category.CategoryMapper
import ru.practicum.main.mapper.event.EventMapper
import ru.practicum.main.mapper.user.UserMapper
import ru.practicum.main.model.event.Event
import ru.practicum.main.model.event.EventRequestStatusUpdateRequest
import ru.practicum.main.model.event.NewEventRequest
import ru.practicum.main.model.request.ParticipationRequest
import ru.practicum.main.model.request.RequestStatus
import ru.practicum.main.repository.EventRepository
import ru.practicum.main.repository.RequestRepository
import ru.practicum.main.service.category.CategoryService
import ru.practicum.main.service.user.UserService
import ru.practicum.stats.client.StatsClient
import spock.lang.Specification

import java.time.Clock
import java.time.LocalDateTime

class EventServiceImplSpec extends Specification {

    def "Should throw EwmIllegalArgumentException when eventDate earlier than an hour"() {
        given:
        def eventRepository = Stub(EventRepository)
        def requestRepository = Stub(RequestRepository)
        def eventMapper = new EventMapper(new CategoryMapper(), new UserMapper(), Clock.systemUTC())
        def categoryService = Mock(CategoryService)
        def userService = Mock(UserService)
        def service = new EventServiceImpl(Mock(StatsClient),
                eventRepository, eventMapper, categoryService, userService, requestRepository, Clock.systemUTC())

        and:
        def newEventRequest = NewEventRequest.builder()
                .eventDate(LocalDateTime.now(Clock.systemUTC())).build()

        when:
        service.createEvent(1L, newEventRequest)
        then:
        thrown(EwmIllegalArgumentException)
    }

    def "Should throw EwmIllegalArgumentException when request status not pending"() {
        given:
        def eventRepository = Stub(EventRepository) {
            findById(1L) >> {
                Optional.of(Event.builder()
                        .moderation(true)
                        .participantLimit(42)
                        .build())
            }
        }
        def requestRepository = Stub(RequestRepository) {
            findAllById(List.of(1L, 2L)) >> {
                List.of(ParticipationRequest.builder().status(RequestStatus.REJECTED).build())
            }
        }
        def eventMapper = new EventMapper(new CategoryMapper(),
                new UserMapper(), Clock.systemUTC())
        def categoryService = Mock(CategoryService)
        def userService = Mock(UserService)
        def service = new EventServiceImpl(Mock(StatsClient),
                eventRepository, eventMapper, categoryService, userService, requestRepository, Clock.systemUTC())

        and:
        def updateStatusRequest = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1L, 2L))
                .status(RequestStatus.CONFIRMED)
                .build()

        when:
        service.updateEventRequests(1L, 1L, updateStatusRequest)
        then:
        thrown(EwmIllegalArgumentException)
    }
}
