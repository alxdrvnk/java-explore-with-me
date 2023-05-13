package ru.practicum.main.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder
public class NewUserRequest {
    @NotBlank
    @Email
    @Size(max = 320)
    String email;
    @NotBlank
    @Size(max = 255)
    String name;

    @JsonCreator
    public NewUserRequest(@JsonProperty(value = "email", required = true) String email,
                          @JsonProperty(value = "name", required = true) String name) {
        this.email = email;
        this.name = name;
    }
}
