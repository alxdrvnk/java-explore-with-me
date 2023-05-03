package ru.practicum.main.model.event;

import lombok.*;

import javax.persistence.Embeddable;

@Builder
@Getter
@With
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    Float lat;
    Float lon;
}
