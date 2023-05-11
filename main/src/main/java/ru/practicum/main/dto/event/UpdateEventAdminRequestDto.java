package ru.practicum.main.dto.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder
public class UpdateEventAdminRequestDto extends UpdateEventRequestDto {
    private StateAction stateAction;

    public enum StateAction implements ru.practicum.main.dto.event.StateAction {
        PUBLISH_EVENT,
        REJECT_EVENT
    }
}
