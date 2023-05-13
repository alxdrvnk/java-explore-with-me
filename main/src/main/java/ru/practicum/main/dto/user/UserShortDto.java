package ru.practicum.main.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserShortDto {

    Long id;
    String name;

    @JsonCreator
    public UserShortDto(@JsonProperty(value = "id", required = true) Long id,
                        @JsonProperty(value = "name", required = true) String name) {
        this.id = id;
        this.name = name;
    }
}
