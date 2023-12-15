package library.proj.gui.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.controllers.LoginController;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public abstract class SceneCreator implements SceneCreatorIf {
    private final String sceneName;
    private final String fxmlSource;
    private final int sceneWidth = 800;
    private final int sceneHeight = 600;

    public SceneCreator(String sceneName, String fxmlSource) {
        this.sceneName = sceneName;
        this.fxmlSource = fxmlSource;
    }

    public Scene createScene(Stage stage, ConfigurableApplicationContext context) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlSource));
            setupController(fxmlLoader, stage, context);
            Parent root = fxmlLoader.load();
            return new Scene(root, sceneWidth, sceneHeight);
        }
        catch (IOException exc) {
            throw new RuntimeException("Unable to load " + sceneName);
        }
    }

    abstract void setupController(FXMLLoader loader, Stage stage, ConfigurableApplicationContext context);
}
