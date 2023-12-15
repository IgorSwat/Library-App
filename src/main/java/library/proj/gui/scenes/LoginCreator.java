package library.proj.gui.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import library.proj.gui.controllers.LoginController;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class LoginCreator implements SceneCreator {

    public Scene createScene(ConfigurableApplicationContext context) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginView.fxml"));
            LoginController controller = new LoginController(context);
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();
            return new Scene(root, 800, 600);
        }
        catch (IOException exc) {
            throw new RuntimeException("Unable to load login scene");
        }
    }

}
