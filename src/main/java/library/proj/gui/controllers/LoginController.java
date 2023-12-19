package library.proj.gui.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.proj.gui.events.ChangeSceneEvent;
import library.proj.gui.scenes.BookListCreator;
import library.proj.gui.scenes.RegisterCreator;
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
    @FXML
    private Label errorLabel;

    public static Person loggedAccount = null;

    public LoginController(Stage stage, ConfigurableApplicationContext context) {
        this.stage = stage;
        this.context = context;
        this.personService = context.getBean(PersonService.class);
    }

    @FXML
    public void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (validateInput(email, password)) {
            Person person = personService.getPerson(email, password);
            if (person == null)
                errorLabel.setText("Niepoprawny email i/lub hasło");
            else {
                loggedAccount = person;
                context.publishEvent(new ChangeSceneEvent(stage, context, new BookListCreator()));
            }
        }
    }

    @FXML
    public void handleRegisterRedirect() {
        context.publishEvent(new ChangeSceneEvent(stage, context, new RegisterCreator()));
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            errorLabel.setText("Nie podano adresu email");
            return false;
        }
        if (password.isEmpty()) {
            errorLabel.setText("Nie podano hasła");
            return false;
        }
        return true;
    }
}
