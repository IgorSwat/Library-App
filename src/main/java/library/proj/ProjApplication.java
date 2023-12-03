package library.proj;

import library.proj.model.Permissions;
import library.proj.model.Person;
import library.proj.repository.PersonsRepository;
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
	public CommandLineRunner demo(PersonsRepository repository){
		return (args -> {
			repository.save(new Person("John", "Doe","cosik@asd.pl", "password", Permissions.ADMIN));
			repository.save(new Person("John", "Smith","abc@asd.pl","pwd",Permissions.USER));
			// fetch all customers
			Logger log = Logger.getLogger("logger");
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			repository.findAll().forEach(customer -> {
				log.info(customer.toString());
			});
		});
	}

}
