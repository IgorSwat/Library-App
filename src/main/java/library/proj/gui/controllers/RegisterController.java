package library.proj.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.proj.gui.events.ChangeSceneEvent;
import library.proj.gui.scenes.BookListCreator;
import library.proj.gui.scenes.LoginCreator;
import library.proj.model.Permissions;
import library.proj.model.Person;
import library.proj.service.PersonService;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.regex.Pattern;

public class RegisterController {
    private final Stage stage;
    private final ConfigurableApplicationContext context;
    private final PersonService personService;

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private Label errorLabel;


    public RegisterController(Stage stage, ConfigurableApplicationContext context) {
        this.stage = stage;
        this.context = context;
        this.personService = context.getBean(PersonService.class);
    }

    @FXML
    public void handleRegister() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String repeatPassword = repeatPasswordField.getText();

        if (validateInput(name, surname, email, password, repeatPassword)) {
            Person person = new Person(name, surname, email, password, Permissions.USER);
            System.out.println("New person created: " + person);    // Test
            personService.savePerson(person);
            LoginController.loggedAccount = person;
            context.publishEvent(new ChangeSceneEvent(stage, context, new BookListCreator()));
        }
    }

    @FXML
    public void handleLoginRedirect() {
        context.publishEvent(new ChangeSceneEvent(stage, context, new LoginCreator()));
    }

    private boolean validateInput(String name, String surname, String email, String password, String repeatPassword) {
        if (name.isEmpty()) {
            errorLabel.setText("Nie podano imienia");
            return false;
        }
        if (surname.isEmpty()) {
            errorLabel.setText("Nie podano nazwiska");
            return false;
        }
        if (email.isEmpty()) {
            errorLabel.setText("Nie podano adresu email");
            return false;
        }
        if (!isEmailValid(email)) {
            errorLabel.setText("Niepoprawny format adresu e-mail");
            return false;
        }
        if (password.isEmpty()) {
            errorLabel.setText("Nie podano hasła");
            return false;
        }
        if (!isPasswordValid(password)) {
            errorLabel.setText("Niepoprawny format hasła (minimalna długośc hasła to 8 znaków)");
            return false;
        }
        if (!password.equals(repeatPassword)) {
            errorLabel.setText("Podane hasła są różne");
            return false;
        }
        if (personService.exists(email)) {
            errorLabel.setText("Podany adres e-mail jest już w użyciu");
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 7;
    }
}
