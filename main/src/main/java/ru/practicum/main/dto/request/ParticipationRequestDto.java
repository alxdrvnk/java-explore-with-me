package ru.practicum.main.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ParticipationRequestDto {
    Long id;
    Long event;
    Long requester;
    String created;
    String status;

    @JsonCreator
    public ParticipationRequestDto(@JsonProperty(value = "id") Long id,
                                   @JsonProperty(value = "event") Long event,
                                   @JsonProperty(value = "requester") Long requester,
                                   @JsonProperty(value = "created") String created,
                                   @JsonProperty(value = "status") String status) {
        this.id = id;
        this.event = event;
        this.requester = requester;
        this.created = created;
        this.status = status;
    }
}
