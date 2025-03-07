package com.example.bookstore.controller;

import com.example.bookstore.BookService;
import com.example.bookstore.model.Book;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    // get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // create new book
    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody Book book, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Book savedBook = bookService.save(book);
        return ResponseEntity.status(201).body(savedBook);
    }


    // delete book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
