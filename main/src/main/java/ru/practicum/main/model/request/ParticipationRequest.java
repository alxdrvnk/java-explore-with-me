package ru.practicum.main.model.request;

import lombok.*;
import ru.practicum.main.model.event.Event;
import ru.practicum.main.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "participation_requests")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
        name = "compilation-events-graph",
        attributeNodes = @NamedAttributeNode(value = "event", subgraph = "subgraph-event"),
        subgraphs = {
                @NamedSubgraph(name = "subgraph-event",
                        attributeNodes = {
                                @NamedAttributeNode(value = "initiator"),
                                @NamedAttributeNode(value = "category")
                        })
        })
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    User requester;
    @Column(name = "created_date")
    LocalDateTime createdDate;
    @With
    @Enumerated(EnumType.STRING)
    RequestStatus status;
}
