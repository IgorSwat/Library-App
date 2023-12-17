package library.proj.gui.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public abstract class SceneCreator implements SceneCreatorIf {
    private final String sceneName;

    protected final FXMLLoader fxmlLoader;

    private static final int sceneWidth = 800;
    private static final int sceneHeight = 600;

    public SceneCreator(String sceneName, String fxmlSource) {
        this.sceneName = sceneName;
        this.fxmlLoader = new FXMLLoader(getClass().getResource(fxmlSource));
    }

    public Scene createScene(Stage stage, ConfigurableApplicationContext context) {
        try {
            setupController(stage, context);
            Parent root = fxmlLoader.load();
            return new Scene(root, sceneWidth, sceneHeight);
        }
        catch (IOException exc) {
            throw new RuntimeException("Unable to load " + sceneName);
        }
    }

    abstract void setupController(Stage stage, ConfigurableApplicationContext context);
}
