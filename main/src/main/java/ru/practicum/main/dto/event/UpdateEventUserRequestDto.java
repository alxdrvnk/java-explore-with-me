package ru.practicum.main.dto.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


@Getter
@Jacksonized
@SuperBuilder
public class UpdateEventUserRequestDto extends UpdateEventRequestDto {
    private StateAction stateAction;

    public enum StateAction implements ru.practicum.main.dto.event.StateAction {
        SEND_TO_REVIEW,
        CANCEL_REVIEW
    }
}
