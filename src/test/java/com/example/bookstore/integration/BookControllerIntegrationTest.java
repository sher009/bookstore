package com.example.bookstore.integration;

import com.example.bookstore.BookService;
import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorRepository authorRepository;

    private Author author;

    @BeforeEach
    public void setup() {
        authorRepository.deleteAll();
        bookService.deleteAll();
        author = authorRepository.save(new Author("Jon", "Lin", LocalDate.of(1990, 5, 15), "USA", "English"));
        assertNotNull(author.getId(), "Author ID should not be null after saving.");
    }


    @Test
    public void testCreateBookWithExistingAuthor() throws Exception {
        String bookJson = "{ \"title\": \"Test Book\", \"publishedDate\": \"2023-01-01\", \"authorId\": " + author.getId() + " }";

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author.id").value(author.getId()));
    }

    @Test
    public void testCreateBookWithNonExistingAuthor() throws Exception {
        // Creating a book with a non-existent author(ID 99999)
        String bookJson = "{ \"title\": \"Orphan Book\", \"publishedDate\": \"2023-01-01\", \"authorId\": 99999 }";

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Author with ID 99999 not found"));
    }

    @Test
    public void testDeleteExistingBook() throws Exception {
        // First, we create a book
        Book book = new Book("Test Book", LocalDate.of(2023, 1, 1), author);
        bookService.save(book);

        // Now delete the book and check if the status 204 is returned
        mockMvc.perform(delete("/books/" + book.getId()))
                .andExpect(status().isNoContent());  // Expect 204 if the book is successfully deleted
    }

    @Test
    public void testDeleteNonExistingBook() throws Exception {
        mockMvc.perform(delete("/books/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateAuthorWithFutureBirthDate() throws Exception {
        String futureAuthorJson = "{ \"firstName\": \"Future\", \"lastName\": \"Author\", \"birthDate\": \"2100-01-01\", \"country\": \"Nowhere\", \"language\": \"Unknown\" }";

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(futureAuthorJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetNonExistingBook() throws Exception {
        mockMvc.perform(get("/books/99999")) // An ID that does not exist
                .andExpect(status().isNotFound());
    }
}
