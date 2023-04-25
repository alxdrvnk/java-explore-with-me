package ru.practicum.main.dto.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class CategoryDto {
    Long id;
    @NotBlank
    String name;

    @JsonCreator
    public CategoryDto(@JsonProperty(value = "id") Long id,
                       @JsonProperty(value = "name", required = true) String name) {
        this.id = id;
        this.name = name;
    }
}
