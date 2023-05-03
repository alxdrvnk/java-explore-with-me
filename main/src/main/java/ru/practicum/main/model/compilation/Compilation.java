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
@With
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Boolean pinned;
    String title;
    @ManyToMany
    @JoinTable(
            name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    Set<Event> events;
}