package com.example.bookstore.service;

import com.example.bookstore.dto.BookRequest;
import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Tag;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;
import com.example.bookstore.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, TagRepository tagRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
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

    public List<Book> getBooksByCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(bookRepository::findByCategory)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    public List<Book> getBooksByTags(Set<Long> tagIds) {
        Set<Tag> tags = tagRepository.findAllById(tagIds).stream().collect(java.util.stream.Collectors.toSet());
        return bookRepository.findByTagsIn(tags);
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

