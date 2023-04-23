package ru.practicum.main.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class EwmError {

    @JsonProperty("message")
    String error;
}
