package library.proj.CLI;

import library.proj.model.Permissions;
import library.proj.model.Person;
import library.proj.repository.PersonsRepository;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class PersonAdder {

    PersonsRepository repository;


    public PersonAdder(PersonsRepository repository){
        this.repository = repository;
    }

    public void invoke(){
        var scanner = new Scanner(System.in);

        System.out.println("Enter person's first name:");
        var firstName = scanner.nextLine();
        System.out.println("Enter person's last name:");
        var lastName = scanner.nextLine();
        System.out.println("Enter person's email:");
        var email = scanner.nextLine();
        System.out.println("Enter person's password");
        var password = scanner.nextLine();
        System.out.println("Enter person's permissions { 0 (Admin) | 1 (Employee) | 2 (User) }:");
        var permissions = Permissions.parse(scanner.nextInt());

        var person = new Person(firstName, lastName, email, password, permissions);
        repository.save(person);
        System.out.println(person);
    }
}
