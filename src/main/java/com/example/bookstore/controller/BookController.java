package com.example.bookstore.controller;

import com.example.bookstore.BookService;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
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
    private final BookRepository bookRepository;

    public BookController(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }


    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok)  // If the book is found, we return it.
                .orElseGet(() -> ResponseEntity.notFound().build());  // If not found, return 404
    }



    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody Book book, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Book savedBook = bookService.save(book);
        return ResponseEntity.status(201).body(savedBook);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();  // Returns 404 if book not found
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();  // Returns 204 if the book was successfully deleted
    }


}
