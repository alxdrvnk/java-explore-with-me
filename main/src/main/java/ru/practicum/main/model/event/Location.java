package ru.practicum.main.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.persistence.Embeddable;

@Value
@Builder
@Embeddable
@AllArgsConstructor
public class Location {
    Float lat;
    Float lon;
}
