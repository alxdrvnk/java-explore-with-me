package ru.practicum.main.model.event;

import lombok.*;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "events")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@With
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String annotation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;
    @With
    @Column(name = "confirmed_requests")
    int confirmedRequests;
    @Column(name = "created_date")
    LocalDateTime createdDate;
    String description;
    @Column(name = "event_date")
    LocalDateTime eventDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    User initiator;
    @Embedded
    Location location;
    Boolean paid;
    @Column(name = "participant_limit")
    int participantLimit;
    @Column(name = "published_date")
    LocalDateTime publishedDate;
    @Column(name = "request_moderation")
    Boolean moderation;
    @Enumerated(EnumType.STRING)
    EventState state;
    String title;
    @Transient
    int views;
}
