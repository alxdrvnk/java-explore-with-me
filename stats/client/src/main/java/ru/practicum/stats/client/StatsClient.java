package ru.practicum.stats.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.stats.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Component
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String url, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new).build());
    }

    public ResponseEntity<Object> saveRequest(String app, String uri, String ip, LocalDateTime date) {
        EndpointHitDto dto = EndpointHitDto.builder()
                .app(app)
                .uri(uri)
                .ip(ip)
                .timestamp(date)
                .build();

        return post("/hit", dto);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uri, Boolean unique) {
        Map<String, Object> pararmeters = Map.of(
                "Start", start,
                "end", end,
                "uris", uri,
                "unique", unique
        );
        return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", pararmeters);
    }

}
