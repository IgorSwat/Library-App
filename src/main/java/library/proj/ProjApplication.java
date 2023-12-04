package library.proj;

import library.proj.CLI.PersonAdder;
import library.proj.model.Permissions;
import library.proj.model.Person;
import library.proj.service.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

@SpringBootApplication
public class ProjApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(PersonService personService, PersonAdder personAdder) {
        return (args -> {
            personService.savePerson(new Person("John", "Doe", "cosik@asd.pl", "password", Permissions.ADMIN));
            personService.savePerson(new Person("John", "Smith", "abc@asd.pl", "pwd", Permissions.USER));
            // fetch all customers
            Logger log = Logger.getLogger("logger");
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            personService.getAllPersons().forEach(customer -> log.info(customer.toString()));

            personAdder.invoke();

            personService.getAllPersons().forEach(customer -> log.info(customer.toString()));

        });
    }

}
