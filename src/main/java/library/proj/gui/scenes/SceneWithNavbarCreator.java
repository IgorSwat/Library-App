package library.proj.gui.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class SceneWithNavbarCreator extends SceneCreator {
    public SceneWithNavbarCreator(String sceneName, String fxmlSource, String stylesheetSource) {super(sceneName, fxmlSource, stylesheetSource);}

    @Override
    public Scene createScene(Stage stage, ConfigurableApplicationContext context) {
        Scene scene = super.createScene(stage, context);
        scene.getStylesheets().add(getClass().getResource("/navbarStyles.css").toExternalForm());
        return scene;
    }
}
