package library.proj.gui.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.controllers.AddBookController;
import org.springframework.context.ConfigurableApplicationContext;

public class AddBookCreator extends SceneCreator {
    private final Stage primaryStage;

    public AddBookCreator(Stage primaryStage) {
        super("Add book dialog", "/addBookView.fxml", "/addBookStyles.css");
        this.primaryStage = primaryStage;
    }

    void setupController(Stage stage, ConfigurableApplicationContext context) {
        AddBookController controller = new AddBookController(primaryStage, stage, context);
        fxmlLoader.setController(controller);
    }
}
