package ru.practicum.main.service.request

import ru.practicum.main.exception.EwmIllegalArgumentException
import ru.practicum.main.exception.EwmNotFoundException
import ru.practicum.main.model.event.Event
import ru.practicum.main.model.event.EventState
import ru.practicum.main.model.request.ParticipationRequest
import ru.practicum.main.model.user.User
import ru.practicum.main.repository.RequestRepository
import ru.practicum.main.service.event.EventService
import ru.practicum.main.service.user.UserService
import spock.lang.Specification

import java.time.Clock

class ParticipationRequestServiceImplSpec extends Specification {

    def "Should throw EwmIllegalArgumentException when user is initiator of event"() {
        given:
        def repository = Stub(RequestRepository)
        def userService = Stub(UserService) {
            getUserById(1L) >> User.builder().id(1L).build()
        }
        def eventService = Stub(EventService) {
            getEventById(1L) >> Event.builder().initiator(User.builder().id(1L).build()).build()
        }
        def service = new ParticipationRequestServiceImpl(repository, userService, eventService, Clock.systemUTC())

        when:
        service.createRequest(1L, 1L)
        then:
        thrown(EwmIllegalArgumentException)
    }


    def "Should throw EwmIllegalArgumentException when limit and confirm requests are equals"() {
        given:
        def repository = Stub(RequestRepository)
        def userService = Stub(UserService) {
            getUserById(1L) >> User.builder().id(1L).build()
        }
        def eventService = Stub(EventService) {
            getEventById(1L) >> Event.builder()
                    .initiator(User.builder().id(1L).build())
                    .participantLimit(1)
                    .confirmedRequests(1)
                    .build()
        }
        def service = new ParticipationRequestServiceImpl(repository, userService, eventService, Clock.systemUTC())

        when:
        service.createRequest(1L, 1L)
        then:
        thrown(EwmIllegalArgumentException)
    }

    def "Should throw EwmIllegalArgumentException when create request and event not published"() {
        given:
        def repository = Stub(RequestRepository)
        def userService = Stub(UserService) {
            getUserById(1L) >> User.builder().id(1L).build()
        }
        def eventService = Stub(EventService) {
            getEventById(1L) >> Event.builder()
                    .initiator(User.builder().id(1L).build())
                    .state(EventState.CANCELED)
                    .build()
        }
        def service = new ParticipationRequestServiceImpl(repository, userService, eventService, Clock.systemUTC())

        when:
        service.createRequest(1L, 1L)
        then:
        thrown(EwmIllegalArgumentException)
    }

    def "Should return EwmNotFoundException when cancel unexpected requests"() {
        given:
        def repository = Stub(RequestRepository) {
            findById(1L) >> Optional.empty()
        }
        def userService = Stub(UserService) {
            getUserById(1L) >> User.builder().id(1L).build()
        }
        def eventService = Stub(EventService)
        def service = new ParticipationRequestServiceImpl(repository, userService, eventService, Clock.systemUTC())

        when:
        service.cancelRequest(1L, 1L)
        then:
        thrown(EwmNotFoundException)
    }

    def "Should return EwmIllegalArgumentException when user not requester cancel request"() {
        given:
        def repository = Stub(RequestRepository) {
            findById(1L) >> Optional.of(
                    ParticipationRequest.builder()
                            .requester(User.builder().id(2L).build()).build())
        }
        def userService = Stub(UserService) {
            getUserById(1L) >> User.builder().id(1L).build()
        }
        def eventService = Stub(EventService)
        def service = new ParticipationRequestServiceImpl(repository, userService, eventService, Clock.systemUTC())

        when:
        service.cancelRequest(1L, 1L)
        then:
        thrown(EwmIllegalArgumentException)
    }
}
