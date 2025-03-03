package com.example.bookstore.controller;


import com.example.bookstore.model.Author;
import com.example.bookstore.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
            this.authorService = authorService;
    }

    @GetMapping
   public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
   }

   @GetMapping("/{id}")
   public ResponseEntity<Author> getAuthorById (@PathVariable Long id){
        Optional<Author> author = authorService.getAuthorById(id);
        return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
   }

   @PostMapping
    public Author createAuthor (@RequestBody Author author){
        return authorService.saveAuthor(author);
   }

   @DeleteMapping("/{id")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
   }

}
