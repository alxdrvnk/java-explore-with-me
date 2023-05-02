package ru.practicum.main.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class EventRequestStatusUpdateRequestDto {
    List<Long> requestIds;
    String status;

    public EventRequestStatusUpdateRequestDto(@JsonProperty(value = "requestIds") List<Long> requestIds,
                                              @JsonProperty(value = "status") String status) {
        this.requestIds = requestIds;
        this.status = status;
    }
}
