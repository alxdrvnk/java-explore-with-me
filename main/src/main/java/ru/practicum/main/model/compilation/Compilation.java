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
    Boolean pinned;
    String title;
    @ManyToMany
    @With
    @JoinTable(
            name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    Set<Event> events;


    public Compilation withTitle(String title) {
        return this.title.equals(title) ?
                this :
                new Compilation(this.id, this.pinned, title, this.events);
    }

    public Compilation withPinned(Boolean pinned) {
        return this.pinned.equals(pinned) ?
                this :
                new Compilation(this.id, pinned, this.title, this.events);
    }
}