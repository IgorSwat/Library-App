package library.proj.gui.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public abstract class SceneCreator implements SceneCreatorIf {
    private final String sceneName;
    private final String stylesheetSource;

    protected final FXMLLoader fxmlLoader;

    private static final int sceneWidth = 800;
    private static final int sceneHeight = 600;

    public SceneCreator(String sceneName, String fxmlSource, String stylesheetSource) {
        this.sceneName = sceneName;
        this.fxmlLoader = new FXMLLoader(getClass().getResource(fxmlSource));
        this.stylesheetSource = stylesheetSource;
    }

    public Scene createScene(Stage stage, ConfigurableApplicationContext context) {
        try {
            setupController(stage, context);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, sceneWidth, sceneHeight);
            if (!stylesheetSource.isEmpty())
                scene.getStylesheets().add(getClass().getResource(stylesheetSource).toExternalForm());
            return scene;
        }
        catch (IOException exc) {
            throw new RuntimeException("Unable to load " + sceneName + " cause: " + exc.getCause());
        }
    }

    abstract void setupController(Stage stage, ConfigurableApplicationContext context);
}
