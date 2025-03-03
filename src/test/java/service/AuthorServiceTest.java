package service;

import com.example.bookstore.AuthorService;
import com.example.bookstore.model.Author;
import com.example.bookstore.repository.AuthorRepository;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class AuthorServiceTest {

    private AuthorRepository authorRepository;
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        authorRepository = mock(AuthorRepository.class);
        authorService = new AuthorService(authorRepository);
    }

    @Test
    void testSaveAuthor() {
        Author author = new Author("Jon", "Lin", LocalDate.of(1990, 5, 10), "USA", "English");
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author savedAuthor = authorService.saveAuthor(author);

        assertNotNull(savedAuthor);
        assertEquals("Jon", savedAuthor.getFirstName());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testGetAllAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of(new Author("Jon", "Lin", LocalDate.of(1990, 5, 10), "USA", "English")));

        java.util.List<Author> authors = authorService.getAllAuthors();

        assertEquals(1, authors.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testGetAuthorByID() {
        Author author = new Author("Jon", "Lin", LocalDate.of(1990, 5, 10), "USA", "English");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Optional<Author> foundAuthor = authorService.getAuthorById(1L);

        assertTrue(foundAuthor.isPresent());
        assertEquals("Jon", foundAuthor.get().getFirstName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveAuthorWithFutureBirthDateThrowsException() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        Author author = new Author("Jon", "Lin", futureDate, "USA", "English");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.saveAuthor(author);
        });

        assertTrue(exception.getMessage().contains("Birth date must be in the past"));
        verify(authorRepository, times(0)).save(any(Author.class)); // Make sure that saving did not happen

    }

    @Test
    void testSaveAuthorWithTodayBirthDateThrowsException() {
        LocalDate today = LocalDate.now();
        Author author = new Author("Jon", "Lin", today, "USA", "English");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.saveAuthor(author);
        });
        assertTrue(exception.getMessage().contains("Birth date must be in the past"));
        verify(authorRepository, times(0)).save(author);
    }

}
