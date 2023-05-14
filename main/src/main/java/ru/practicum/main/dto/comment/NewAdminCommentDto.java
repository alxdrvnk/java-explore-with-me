package ru.practicum.main.dto.comment;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Size;

@Value
@Builder
public class NewAdminCommentDto {
    @Size(max = 6000)
    String text;
}
