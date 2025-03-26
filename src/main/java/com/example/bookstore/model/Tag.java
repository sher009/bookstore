package com.example.bookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tag name cannot be null")
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Book> books;

    public Tag(String name, Set<Book> books) {
        this.name = name;
        this.books = books;
    }

}
