package library.proj.gui.scenes;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import library.proj.gui.controllers.LoginController;
import org.springframework.context.ConfigurableApplicationContext;

public class LoginCreator extends SceneCreator {

    public LoginCreator() {
        super("Login scene", "/loginView.fxml");
    }

    void setupController(Stage stage, ConfigurableApplicationContext context) {
        LoginController controller = new LoginController(stage, context);
        fxmlLoader.setController(controller);
    }

}
