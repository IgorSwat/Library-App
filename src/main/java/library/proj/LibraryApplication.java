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

            booksService.createBook(new Book("Krzyżacy", "Henryk Sienkiewicz", "-",
                    "Zbyszko z Bodańca wstał z samego rańca", Status.AVAILABLE.ordinal()));
            booksService.createBook(new Book("Dziady", "Adaś M.", "-",
                    "A bolały go kości i plery", Status.AVAILABLE.ordinal()));
            booksService.createBook(new Book("Example 1", "Anonymous", "-",
                    "Description 1", Status.AVAILABLE.ordinal()));
            booksService.createBook(new Book("Example 2", "Anonymous", "-",
                    "Description 2", Status.AVAILABLE.ordinal()));
            booksService.createBook(new Book("Example 3", "Anonymous", "-",
                    "Description 3", Status.NOT_AVAILABLE.ordinal()));
            booksService.createBook(new Book("Example 4", "Anonymous", "-",
                    "Description 4", Status.AVAILABLE.ordinal()));
            booksService.createBook(new Book("Example 5", "Anonymous", "-",
                    "Description 5", Status.AVAILABLE.ordinal()));
            booksService.createBook(new Book("Example 6", "Anonymous", "-",
                    "Description 6", Status.AVAILABLE.ordinal()));
        });
    }

}
