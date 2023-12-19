package library.proj.gui.scenes;

import javafx.stage.Stage;
import library.proj.gui.controllers.LoginController;
import org.springframework.context.ConfigurableApplicationContext;

public class LoginCreator extends SceneCreator {
    public LoginCreator() {
        super("Login scene", "/loginView.fxml", "/loginAndRegisterStyles.css");
    }

    void setupController(Stage stage, ConfigurableApplicationContext context) {
        LoginController controller = new LoginController(stage, context);
        fxmlLoader.setController(controller);
    }

}
