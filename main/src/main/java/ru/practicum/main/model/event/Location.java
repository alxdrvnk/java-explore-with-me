package ru.practicum.main.model.event;

import lombok.*;

import javax.persistence.Embeddable;

@Builder
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private Float lat;
    private Float lon;
}
