package library.proj;

import javafx.application.Application;
import library.proj.CLI.PersonAdder;
import library.proj.gui.LibraryUI;
import library.proj.model.Permissions;
import library.proj.model.Person;
import library.proj.service.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        Application.launch(LibraryUI.class, args);
    }

//    @Bean
//    public CommandLineRunner demo(PersonService personService, PersonAdder personAdder) {
//        return (args -> {
//            personService.savePerson(new Person("John", "Doe", "cosik@asd.pl", "password", Permissions.ADMIN));
//            personService.savePerson(new Person("John", "Smith", "abc@asd.pl", "pwd", Permissions.USER));
//            // fetch all customers
//            Logger log = Logger.getLogger("logger");
//            log.info("Customers found with findAll():");
//            log.info("-------------------------------");
//            personService.getAllPersons().forEach(customer -> log.info(customer.toString()));
//
//            while (true) {
//                personAdder.invoke();
//                personService.getAllPersons().forEach(customer -> log.info(customer.toString()));
//            }
//
//
//        });
//    }

}
