package ru.practicum.main.mapper.comment;

import org.springframework.stereotype.Component;
import ru.practicum.main.dto.comment.CommentDto;
import ru.practicum.main.dto.comment.NewAdminCommentDto;
import ru.practicum.main.model.comment.AdminEventComment;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public CommentDto toCommentDto(AdminEventComment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .createdDate(comment.getCreatedDate())
                .build();
    }

    public List<CommentDto> toCommentDtoList(List<AdminEventComment> comments) {
        if (comments.isEmpty()) {
            return Collections.emptyList();
        }
        return comments.stream().map(this::toCommentDto).collect(Collectors.toList());
    }

    public AdminEventComment toAdminEventComment(NewAdminCommentDto comment) {
        return AdminEventComment.builder()
                .text(comment.getText())
                .build();
    }
}
