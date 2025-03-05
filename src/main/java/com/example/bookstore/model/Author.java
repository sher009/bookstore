package com.example.bookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.example.bookstore.validation.PastOrPresent;


import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "authors")
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
    @Column(name = "birthDate")
    private LocalDate birthDate;

    @Column(name = "country")
    private String country;

    @Column(name = "language")
    private String language;

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

    //getters setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
