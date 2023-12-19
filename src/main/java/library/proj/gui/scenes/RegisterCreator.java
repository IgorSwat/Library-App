package library.proj.gui.scenes;

import javafx.stage.Stage;
import library.proj.gui.controllers.RegisterController;
import org.springframework.context.ConfigurableApplicationContext;

public class RegisterCreator extends SceneCreator {
    public RegisterCreator() {
        super("Registration scene", "/registerView.fxml", "/loginAndRegisterStyles.css");
    }

    void setupController(Stage stage, ConfigurableApplicationContext context) {
        RegisterController controller = new RegisterController(stage, context);
        fxmlLoader.setController(controller);
    }
}
