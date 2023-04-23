package ru.practicum.main.dto.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class NewCategoryDto {
    @NotNull
    @NotBlank
    String name;

    @JsonCreator
    public NewCategoryDto(@JsonProperty(value = "name", required = true) String name) {
        this.name = name;
    }
}
