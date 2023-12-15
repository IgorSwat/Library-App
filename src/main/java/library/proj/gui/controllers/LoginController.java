package library.proj.gui.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.proj.model.Person;
import library.proj.service.PersonService;
import org.springframework.context.ConfigurableApplicationContext;

public class LoginController {
    private final Stage stage;
    private final ConfigurableApplicationContext context;
    private final PersonService personService;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    public LoginController(Stage stage, ConfigurableApplicationContext context) {
        this.stage = stage;
        this.context = context;
        this.personService = context.getBean(PersonService.class);
    }

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
