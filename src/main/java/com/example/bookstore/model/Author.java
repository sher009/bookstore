package com.example.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.example.bookstore.validation.PastOrPresent;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Author {
    // attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotNull(message = "First name cannot be null")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @PastOrPresent(message = "Birth date must be in the past or present") // use my own annotation
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "country")
    private String country;

    @Column(name = "language")
    private String language;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    //No-argument constructor(required for Hibernate)
    public Author() {
    }

    //Constructor
    public Author(String firstName, String lastName, LocalDate birthDate, String country, String language) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.country = country;
        this.language = language;
    }

}
