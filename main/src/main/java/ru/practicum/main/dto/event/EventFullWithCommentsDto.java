package ru.practicum.main.dto.event;

import lombok.Builder;
import lombok.Getter;
import ru.practicum.main.dto.category.CategoryDto;
import ru.practicum.main.dto.comment.CommentDto;
import ru.practicum.main.dto.user.UserShortDto;

import java.util.List;

@Getter
public class EventFullWithCommentsDto extends EventFullDto {
    private final List<CommentDto> comments;

    @Builder(builderMethodName = "EventFullWithCommentsDtoBuilder")
    public EventFullWithCommentsDto(Long id,
                                    String annotation,
                                    CategoryDto category,
                                    Integer confirmedRequests,
                                    String createdOn,
                                    String description,
                                    String eventDate,
                                    UserShortDto initiator,
                                    LocationDto location,
                                    Boolean paid,
                                    Integer participantLimit,
                                    String publishedOn,
                                    Boolean requestModeration,
                                    String state,
                                    String title,
                                    Integer views,
                                    List<CommentDto> comments) {
        super(id,annotation,category,confirmedRequests,createdOn,description,eventDate,initiator,location,paid,
                participantLimit,publishedOn,requestModeration,state,title,views);
        this.comments = comments;
    }
}
