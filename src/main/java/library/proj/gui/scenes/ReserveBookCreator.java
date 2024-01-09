package library.proj.gui.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.controllers.ReserveBookController;
import library.proj.model.Book;
import org.springframework.context.ConfigurableApplicationContext;

public class ReserveBookCreator extends SceneCreator {
    private final Stage primaryStage;
    private final Book book;

    public ReserveBookCreator(Stage primaryStage, Book book) {
        super("Reserve book dialog", "/reserveBookView.fxml", "/rentBookStyles.css");
        this.primaryStage = primaryStage;
        this.book = book;
    }

    @Override
    public Scene createScene(Stage stage, ConfigurableApplicationContext context){
        Scene scene = super.createScene(stage, context);
        ReserveBookController controller = fxmlLoader.getController();
        controller.initialize();
        return scene;
    }

    void setupController(Stage stage, ConfigurableApplicationContext context) {
        ReserveBookController controller = new ReserveBookController(primaryStage, stage, context, book);
        fxmlLoader.setController(controller);
    }
}
