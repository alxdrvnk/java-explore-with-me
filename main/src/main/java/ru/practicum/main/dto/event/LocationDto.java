package ru.practicum.main.dto.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class LocationDto {
    @NotNull
    Float lat;
    @NotNull
    Float lon;

    @JsonCreator
    public LocationDto(@JsonProperty(value = "lat") Float lat,
                       @JsonProperty(value = "lon") Float lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
