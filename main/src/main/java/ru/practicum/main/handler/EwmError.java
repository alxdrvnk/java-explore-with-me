package ru.practicum.main.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EwmError {
    @JsonProperty("status")
    String status;
    @JsonProperty("reason")
    String reason;
    @JsonProperty("message")
    String error;
    @JsonProperty("timestamps")
    String timestamp;
}
