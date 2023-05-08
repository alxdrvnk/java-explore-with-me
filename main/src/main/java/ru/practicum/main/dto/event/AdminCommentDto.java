package ru.practicum.main.dto.event;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class AdminCommentDto {
    Long id;

    @NotNull
    @NotBlank
    String text;
}
