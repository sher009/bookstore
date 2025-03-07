package com.example.bookstore.integration;

import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testCreateAndRetrieveBook() throws Exception {
        // Створюємо автора і зберігаємо його в базі
        Author author = new Author("Jon", "Lin", LocalDate.of(1990, 5, 15), "USA", "English");
        authorRepository.save(author);

        // Створюємо нову книгу
        String newBookJson = "{ \"title\": \"Test Book\", \"publishedDate\": \"2023-01-01\", \"author\": { \"id\": " + author.getId() + " } }";

        // Створюємо нову книгу
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newBookJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Test Book")))
                .andExpect(jsonPath("$.author.firstName", is("Jon")))
                .andExpect(jsonPath("$.author.lastName", is("Lin")));

        // Отримуємо всі книги і перевіряємо, чи є нова книга
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].title", is("Test Book")));
    }
}
