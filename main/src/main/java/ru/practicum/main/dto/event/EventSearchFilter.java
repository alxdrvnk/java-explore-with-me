package ru.practicum.main.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.main.converter.DateTimeConverter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

@Value
@Builder
@Jacksonized
public class EventSearchFilter {
    String text;
    Long[] users;
    String[] states;
    Long[] categories;
    @Pattern(regexp = DateTimeConverter.DateTimeRegEx)
    String rangeStart;
    @Pattern(regexp = DateTimeConverter.DateTimeRegEx)
    String rangeEnd;
    Boolean paid;
    String sort;
    Boolean onlyAvailable;
    @PositiveOrZero(message = "From value must be a positive or zero")
    Integer from;
    @Positive(message = "Size value must be a positive")
    Integer size;

    @JsonCreator
    public EventSearchFilter(@JsonProperty(value = "text") String text,
                             @JsonProperty(value = "users") Long[] users,
                             @JsonProperty(value = "states") String[] states,
                             @JsonProperty(value = "categories") Long[] categories,
                             @JsonProperty(value = "rangeStart") String rangeStart,
                             @JsonProperty(value = "rangeEnd") String rangeEnd,
                             @JsonProperty(value = "paid") Boolean paid,
                             @JsonProperty(value = "sort") String sort,
                             @JsonProperty(value = "onlyAvailable") Boolean onlyAvailable,
                             @JsonProperty(value = "from") Integer from,
                             @JsonProperty(value = "size") Integer size) {
        this.text = text;
        this.users = users;
        this.states = states;
        this.categories = categories;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.paid = paid;
        this.sort = sort;
        this.onlyAvailable = Objects.requireNonNullElse(onlyAvailable, false);
        this.from = Objects.requireNonNullElse(from, 0);
        this.size = Objects.requireNonNullElse(size, 10);
    }
}
