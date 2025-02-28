import com.example.bookstore.model.Author;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // створюю нового автора; create new author
        Author author = new Author("Igor", "Omega", LocalDate.of(1998,12,12), "Moldova", "English");

        System.out.println("Author: " + author.getFirstName() + " " + author.getLastName());
        System.out.println("Birth Date: " + author.getBirthDate());
        System.out.println("Language: " + author.getLanguage());
    }
}