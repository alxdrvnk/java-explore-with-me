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
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    User requester;
    @Column(name = "created_date")
    LocalDateTime createdDate;
    @With
    @Enumerated(EnumType.STRING)
    RequestStatus status;
}
