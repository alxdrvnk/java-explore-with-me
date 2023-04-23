package ru.practicum.main.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String email;
    String name;
}
