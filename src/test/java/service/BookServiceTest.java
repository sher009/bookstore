package service;

import com.example.bookstore.AuthorService;
import com.example.bookstore.BookService;
import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        AuthorRepository authorRepository = mock(AuthorRepository.class);
        bookService = new BookService(bookRepository, authorRepository);
    }

    @Test
    void testSave() {
        Author author = new Author("Jon", "Lin", LocalDate.of(1990, 5, 10), "USA", "English");
        Book book = new Book("The Great Adventure", LocalDate.of(2020, 5, 10), author);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book saveBook = bookService.save(book);

        assertNotNull(saveBook);
        assertEquals("The Great Adventure", saveBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testGetById() {
        Author author = new Author("Jon", "Lin", LocalDate.of(1990, 5, 10), "USA", "English");
        Book book = new Book("The Great Adventure", LocalDate.of(2020, 5, 10), author);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getById(1L);

        assertTrue(foundBook.isPresent());
        assertEquals("The Great Adventure", foundBook.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testDelete(){
        // Arrange: set up the ID of the book to delete
        Long bookId = 1L;

        //Act: call the delete method
        bookService.delete(bookId);

        // Assert: verify that deleteById was called with the correct ID
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void testGetAll() {
        Author author1 = new Author("Jon", "Lin", LocalDate.of(1990, 5, 10), "USA", "English");
        Author author2 = new Author("Jane", "Doe", LocalDate.of(1985, 4, 22), "UK", "English");

        Book book1 = new Book("The Great Adventure", LocalDate.of(2020, 5, 10), author1);
        Book book2 = new Book("Another Journey", LocalDate.of(2019, 3, 12), author2);

        List<Book> books = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> foundBooks = bookService.getAll();

        assertNotNull(foundBooks);
        assertEquals(2, foundBooks.size());
        assertEquals("The Great Adventure", foundBooks.get(0).getTitle());
        assertEquals("Another Journey", foundBooks.get(1).getTitle());
        verify(bookRepository, times(1)).findAll();
    }


}
