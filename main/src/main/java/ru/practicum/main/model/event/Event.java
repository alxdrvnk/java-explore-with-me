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
@NamedEntityGraph(
        name = "event-category-initiator-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "category"),
                @NamedAttributeNode(value = "initiator")
        })
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String annotation;
    @With
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
    @With
    @Column(name = "confirmed_requests")
    int confirmedRequests;
    @With
    @Column(name = "created_date")
    LocalDateTime createdDate;
    String description;
    @With
    @Column(name = "event_date")
    LocalDateTime eventDate;
    @With
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    User initiator;
    @With
    @Embedded
    Location location;
    @With
    Boolean paid;
    @With
    @Column(name = "participant_limit")
    int participantLimit;
    @With
    @Column(name = "published_date")
    LocalDateTime publishedDate;
    @With
    @Column(name = "request_moderation")
    Boolean moderation;
    @With
    @Enumerated(EnumType.STRING)
    EventState state;
    String title;
    @With
    @Transient
    int views;

    public Event withTitle(String title) {
        return this.title.equals(title) ?
                this :
                new Event(this.id, this.annotation, this.category,
                        this.confirmedRequests, this.createdDate, this.description, this.eventDate,
                        this.initiator, this.location, this.paid, this.participantLimit,
                        this.publishedDate, this.moderation, this.state, title, this.views);
    }

    public Event withDescription(String description) {
        return this.description.equals(description) ?
                this :
                new Event(this.id, this.annotation, this.category,
                        this.confirmedRequests, this.createdDate, description, this.eventDate,
                        this.initiator, this.location, this.paid, this.participantLimit,
                        this.publishedDate, this.moderation, this.state, this.title, this.views);
    }

    public Event withAnnotation(String annotation) {
        return this.annotation.equals(annotation) ?
                this :
                new Event(this.id, annotation, this.category,
                        this.confirmedRequests, this.createdDate, this.description, this.eventDate,
                        this.initiator, this.location, this.paid, this.participantLimit,
                        this.publishedDate, this.moderation, this.state, this.title, this.views);
    }
}