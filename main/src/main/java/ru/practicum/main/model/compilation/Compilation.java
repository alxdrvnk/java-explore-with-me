package ru.practicum.main.model.compilation;


import lombok.*;

import javax.persistence.*;

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
}
