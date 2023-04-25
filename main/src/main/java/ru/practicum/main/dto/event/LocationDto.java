package ru.practicum.main.dto.event;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LocationDto {
    Float lat;
    Float lon;
}
