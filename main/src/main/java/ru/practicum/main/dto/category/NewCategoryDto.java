package ru.practicum.main.dto.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder
public class NewCategoryDto {
    @NotBlank
    @Size(max = 255)
    String name;

    @JsonCreator
    public NewCategoryDto(@JsonProperty(value = "name", required = true) String name) {
        this.name = name;
    }
}
