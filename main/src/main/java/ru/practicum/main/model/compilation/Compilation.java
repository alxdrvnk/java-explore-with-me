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
@NamedEntityGraph(
        name = "compilation-events-graph",
        attributeNodes = @NamedAttributeNode(value = "events", subgraph = "subgraph-event"),
        subgraphs = {
                @NamedSubgraph(name = "subgraph-event",
                        attributeNodes = {
                        @NamedAttributeNode(value = "initiator"),
                        @NamedAttributeNode(value = "category")
                })
        })
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Boolean pinned;
    String title;
    @ManyToMany
    Set<Event> events;
}