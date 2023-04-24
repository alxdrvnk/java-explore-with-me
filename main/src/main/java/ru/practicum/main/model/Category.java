package ru.practicum.main.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "categories")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @With
    String name;
}
