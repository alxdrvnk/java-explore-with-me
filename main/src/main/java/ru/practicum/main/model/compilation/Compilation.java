package ru.practicum.main.model.compilation;


import lombok.*;
import ru.practicum.main.model.event.Event;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@Table(name = "compilations")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Set<Event> events;
}
