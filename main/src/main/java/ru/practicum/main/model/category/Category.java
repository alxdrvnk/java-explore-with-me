package ru.practicum.main.model.category;

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

    String name;

    public Category withName(String name) {
        return this.name.equals(name) ? this : new Category(this.id, name);
    }
}
