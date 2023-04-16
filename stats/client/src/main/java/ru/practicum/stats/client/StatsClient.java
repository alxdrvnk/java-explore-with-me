package ru.practicum.stats.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Component
public class StatsClient {
    private final WebClient webClient;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatsClient(@Value("${stats-service.url}") String url) {
        this.webClient = WebClient.create(url);
    }

    public void saveRequest(String app, String path, String ip, LocalDateTime time) {
        EndpointHitDto dto = EndpointHitDto.builder()
                .app(app)
                .uri(path)
                .ip(ip)
                .timestamp(time.format(formatter))
                .build();
        webClient
                .post()
                .uri("/hit")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uri, Boolean unique) {
        return webClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path("/stats")
                                .queryParam("start", start.format(formatter))
                                .queryParam("end", end.format(formatter))
                                .queryParam("uris", uri)
                                .queryParam("unique", unique)
                                .build())
                .retrieve()
                .bodyToFlux(ViewStatsDto.class)
                .collectList()
                .block();
    }
}
