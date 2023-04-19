package ru.practicum.stats.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.practicum.stats.converter.DateTimeConverter
import ru.practicum.stats.dto.EndpointHitDto
import ru.practicum.stats.mapper.StatsMapper
import ru.practicum.stats.model.EndpointHit
import ru.practicum.stats.service.StatsService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class StatsControllerSpec extends Specification {

    private final DateTimeConverter dateTimeConverter = new DateTimeConverter()
    private final StatsMapper statsMapper = new StatsMapper(dateTimeConverter)
    private final ObjectMapper objectMapper = new ObjectMapper()

    def "Should create EndpointHit"() {
        given:
        def service = Mock(StatsService)
        def controller = new StatsController(service, statsMapper, dateTimeConverter)
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .build()
        and:
        def dto = EndpointHitDto.builder()
                .app("testapp")
                .ip("192.168.0.1")
                .uri("testuri")
                .timestamp("2007-09-01 12:00:00")
                .build()
        when:
        def request = post("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        and:
        server.perform(request)
                .andExpect(status().isOk())

        then:
        1 * service.create(_ as EndpointHit)
    }
}
