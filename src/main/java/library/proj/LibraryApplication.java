package library.proj;

import javafx.application.Application;
import library.proj.gui.LibraryUI;
import library.proj.model.Permissions;
import library.proj.model.Person;
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
    public CommandLineRunner setAdmin(PersonService personService) {
        return (args -> {
            personService.savePerson(new Person("Rzegorz", "Gogus", "abc@asd.pl", "password", Permissions.ADMIN));
        });
    }

}
