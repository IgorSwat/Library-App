package library.proj.gui.scenes;

import javafx.scene.Scene;
import org.springframework.context.ConfigurableApplicationContext;

// An interface implemented by each scene creators
// A scene creator is a class responsible for creating appropriate JavaFX scene from FXML file and (if needed) linking it
// with correct controller class. Every scene used inside app should have its own creator.
public interface SceneCreator {
    Scene createScene(ConfigurableApplicationContext context);
}
