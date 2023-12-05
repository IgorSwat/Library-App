package library.proj.CLI;

import library.proj.model.Permissions;
import library.proj.model.Person;
import library.proj.service.PersonService;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.regex.Pattern;

@Component
public class PersonAdder {

    PersonService personService;


    public PersonAdder(PersonService personService) {
        this.personService = personService;
    }

    public void invoke() {
        var scanner = new Scanner(System.in);

        System.out.println("Enter person's first name:");
        var firstName = scanner.nextLine().trim();

        System.out.println("Enter person's last name:");
        var lastName = scanner.nextLine().trim();

        System.out.println("Enter person's email:");
        var email = scanner.nextLine().trim();

        System.out.println("Enter person's password");
        var password = scanner.nextLine();

        System.out.println("Enter person's permissions { 0 (Admin) | 1 (Employee) | 2 (User) }:");
        var permissions = Permissions.parse(scanner.nextInt());

        var person = new Person(firstName, lastName, email, password, permissions);
        if (!isEmailValid(email)) {
            System.out.println("Invalid email");
        } else if (!isPasswordValid(password)) {
            System.out.println("Password must have length of at least 8 characters");
        } else if (personService.exists(email)) {
            System.out.println("Person with given email already exists");
        } else {
            personService.savePerson(person);
            System.out.println(person);
        }
    }

    private boolean isEmailValid(String email) {
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 7;
    }
}
