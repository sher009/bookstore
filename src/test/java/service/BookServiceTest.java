package service;

import com.example.bookstore.BookService;
import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    void testSaveBook() {
        Author author = new Author("Jon", "Lin", LocalDate.of(1990, 5, 10), "USA", "English");
        Book book = new Book("The Great Adventure", LocalDate.of(2020, 5, 10), author);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book saveBook = bookService.saveBook(book);

        assertNotNull(saveBook);
        assertEquals("The Great Adventure", saveBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testGetBookById() {
        Author author = new Author("Jon", "Lin", LocalDate.of(1990, 5, 10), "USA", "English");
        Book book = new Book("The Great Adventure", LocalDate.of(2020, 5, 10), author);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(1L);

        assertTrue(foundBook.isPresent());
        assertEquals("The Great Adventure", foundBook.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }
}
