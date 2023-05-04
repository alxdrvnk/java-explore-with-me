package ru.practicum.main.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LocationDto {
    Float lat;
    Float lon;

    @JsonCreator
    public LocationDto(@JsonProperty(value = "lat") Float lat,
                       @JsonProperty(value = "lon") Float lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
