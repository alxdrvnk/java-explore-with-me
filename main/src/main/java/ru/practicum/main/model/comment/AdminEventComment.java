package ru.practicum.main.model.comment;

import lombok.*;
import ru.practicum.main.model.event.Event;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "admin_event_comments")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
        name = "admin_comment-events-graph-requester",
        attributeNodes = {
                @NamedAttributeNode(value = "event", subgraph = "subgraph-event")
        },
        subgraphs = {
                @NamedSubgraph(name = "subgraph-event",
                        attributeNodes = {
                                @NamedAttributeNode(value = "initiator"),
                                @NamedAttributeNode(value = "category")
                        })
        })
public class AdminEventComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @With
    @JoinColumn(name = "event_id")
    Event event;

    String text;

    @With
    @Column(name = "is_corrected")
    boolean corrected;

    @With
    @Column(name = "created_date")
    LocalDateTime createdDate;
}
