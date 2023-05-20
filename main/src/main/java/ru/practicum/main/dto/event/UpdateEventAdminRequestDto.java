package ru.practicum.main.dto.event;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.main.dto.comment.NewAdminCommentDto;

@Getter
@Jacksonized
@SuperBuilder
@ToString
public class UpdateEventAdminRequestDto extends UpdateEventRequestDto {
    private StateAction stateAction;
    private NewAdminCommentDto comment;

    public enum StateAction implements ru.practicum.main.dto.event.StateAction {
        PUBLISH_EVENT,
        REJECT_EVENT
    }
}
