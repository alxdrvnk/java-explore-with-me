package ru.practicum.main.model.event;

import lombok.*;
import ru.practicum.main.model.category.Category;
import ru.practicum.main.model.comment.AdminEventComment;
import ru.practicum.main.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
@ToString
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
    @Transient
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
    @Transient
    @With
    List<AdminEventComment> comments;
    @With
    @Transient
    int views;

    public Event withTitle(String title) {
        return this.title.equals(title) ?
                this :
                new Event(this.id, this.annotation, this.category,
                        this.confirmedRequests, this.createdDate, this.description, this.eventDate,
                        this.initiator, this.location, this.paid, this.participantLimit,
                        this.publishedDate, this.moderation, this.state, title, this.comments, this.views);
    }

    public Event withDescription(String description) {
        return this.description.equals(description) ?
                this :
                new Event(this.id, this.annotation, this.category,
                        this.confirmedRequests, this.createdDate, description, this.eventDate,
                        this.initiator, this.location, this.paid, this.participantLimit,
                        this.publishedDate, this.moderation, this.state, this.title, this.comments, this.views);
    }

    public Event withAnnotation(String annotation) {
        return this.annotation.equals(annotation) ?
                this :
                new Event(this.id, annotation, this.category,
                        this.confirmedRequests, this.createdDate, this.description, this.eventDate,
                        this.initiator, this.location, this.paid, this.participantLimit,
                        this.publishedDate, this.moderation, this.state, this.title, this.comments, this.views);
    }


    public Event(
            Long id,
            String annotation,
            Category category,
            long confirmedRequests,
            LocalDateTime createdDate,
            String description,
            LocalDateTime eventDate,
            User initiator,
            Location location,
            Boolean paid,
            int participantLimit,
            LocalDateTime publishedDate,
            Boolean moderation,
            EventState state,
            String title) {
        this.id = id;
        this.annotation = annotation;
        this.category = category;
        this.confirmedRequests = (int)confirmedRequests;
        this.createdDate = createdDate;
        this.description = description;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.publishedDate = publishedDate;
        this.moderation = moderation;
        this.state = state;
        this.title = title;
    }
}