package com.example.bookstore;

import com.example.bookstore.dto.BookRequest;
import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    @Transactional
    public Book save(BookRequest request) {
        // Checking if the author is in the database
        Long authorId = request.getAuthorId();
        if (authorId != null) {
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new EntityNotFoundException("Author with ID " + authorId + " not found"));

            Book book = new Book(request.getTitle(), request.getPublishedDate(), author);

            return bookRepository.save(book);

        }

        throw new EntityNotFoundException("Author cannot be null");
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }


    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book with ID " + id + " not found");
        }
        bookRepository.deleteById(id);
    }


    public void deleteAll() {
        bookRepository.deleteAll();
    }
}

