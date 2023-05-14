package ru.practicum.main.dto.event;

import lombok.Builder;
import lombok.Getter;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.comment.CommentDto;
import ru.practicum.main.dto.user.UserShortDto;

import java.util.List;

@Getter
public class EventShortWithCommentsDto extends EventShortDto{
    private final List<CommentDto> comments;

    @Builder(builderMethodName = "EventShortWithCommentsDtoBuilder")
    public EventShortWithCommentsDto(
            Long id,
            String annotation,
            CategoryDto category,
            Integer confirmedRequests,
            String eventDate,
            UserShortDto initiator,
            Boolean paid,
            String title,
            Integer views,
            List<CommentDto> comments) {

        super(id, annotation, category, confirmedRequests, eventDate, initiator, paid, title, views);
        this.comments = comments;
    }
}
