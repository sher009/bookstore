package com.example.bookstore.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;

import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;


@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title cannot be null")
    private String title;

    @Column(name = "published_date")
    @PastOrPresent(message = "Published date cannot be in the future")
    private LocalDate publishedDate;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    // No-argument constructor (required for Hibernate)
    public Book() {
    }

    // Constructors
    public Book(String title, LocalDate publishedDate, Author author) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.author = author;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
