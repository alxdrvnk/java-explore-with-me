package ru.practicum.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
@Jacksonized
public class EndpointHitDto {
     Long id;
     @NotBlank
     String app;
     String uri;
     @NotBlank
     String ip;
     @NotNull
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
     String timestamp;
}
