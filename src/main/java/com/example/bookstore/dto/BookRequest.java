package com.example.bookstore.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import com.example.bookstore.validation.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class BookRequest {

    @NotNull(message = "Title cannot be null")
    private String title;

    @PastOrPresent(message = "Published date cannot be in the future")
    private LocalDate publishedDate;

    @NotNull(message = "Author ID cannot be null")
    @Min(value = 0, message = "Author ID must be positive")
    private Long authorId;

    // Constructor
    public BookRequest() {
    }

    public BookRequest(String title, LocalDate publishedDate, Long authorId) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.authorId = authorId;
    }

}
