package ru.practicum.stats.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import javax.persistence.*;
import java.time.LocalDateTime;

@Value
@Entity
@Builder
@Table(name = "endpoint_hit", schema = "public")
@Getter
@AllArgsConstructor
public class EndpointHit {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;
     String app;
     String uri;
     String ip;
     LocalDateTime timestamp;
}