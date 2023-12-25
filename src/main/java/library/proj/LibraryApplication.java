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
            booksService.createBook(new Book("Limes Inferior", "Janusz Zajdel", "https://iskry.com.pl/wp-content/uploads/2017/08/549-1500-Limes-inferior-1987.jpg", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", Status.AVAILABLE.ordinal()));
        });
    }

    private void addBookExamples(BooksService booksService, int noExamples) {
        for (int i = 0; i < noExamples; i++) {
            booksService.createBook(new Book("Example " + (i + 1), "Anonymous", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/640px-No-Image-Placeholder.svg.png",
                    "Some description", Status.AVAILABLE.ordinal()));
        }
    }

}
