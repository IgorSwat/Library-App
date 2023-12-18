package library.proj;

import javafx.application.Application;
import library.proj.gui.LibraryUI;
import library.proj.model.Book;
import library.proj.model.Permissions;
import library.proj.model.Person;
import library.proj.model.Status;
import library.proj.service.BooksService;
import library.proj.service.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        Application.launch(LibraryUI.class, args);
    }

    @Bean
    public CommandLineRunner addExampleData(PersonService personService, BooksService booksService) {
        return (args -> {
            personService.savePerson(new Person("Rzegorz", "Gogus", "abc@asd.pl", "password", Permissions.ADMIN));
            personService.savePerson(new Person("Baran", "Adamski", "baran@gmail.com", "password", Permissions.USER));

            addBookExamples(booksService, 18);
        });
    }

    private void addBookExamples(BooksService booksService, int noExamples) {
        for (int i = 0; i < noExamples; i++) {
            booksService.createBook(new Book("Example " + Integer.toString(i + 1), "Anonymous", "-",
                    "Some description", Status.AVAILABLE.ordinal()));
        }
    }

}
