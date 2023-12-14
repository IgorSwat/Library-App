package library.proj.gui.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import library.proj.model.Person;
import library.proj.service.PersonService;

public class LoginController {
    private final PersonService personService;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    public LoginController(PersonService personService) {this.personService = personService;}

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        Person person = personService.getPerson(email, password);
        if (person != null)
            System.out.println("Succesfully logged as " + person.getFirstName() + person.getLastName());
        else
            System.out.println("Invalid email or password");
    }
}
