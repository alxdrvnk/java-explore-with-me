package ru.practicum.main.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {

    Long id;
    String name;
    String email;

    @JsonCreator
    public UserDto(@JsonProperty(value = "id") Long id,
                   @JsonProperty(value = "name", required = true) String name,
                   @JsonProperty(value = "email", required = true) String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
