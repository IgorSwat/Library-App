package library.proj.gui.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.controllers.RentBookController;
import library.proj.model.Book;
import org.springframework.context.ConfigurableApplicationContext;

public class RentBookCreator extends SceneCreator {
    private final Stage primaryStage;
    private final Book book;

    public RentBookCreator(Stage primaryStage, Book book) {
        super("Rent book dialog", "/rentBookView.fxml", "/addBookStyles.css");
        this.primaryStage = primaryStage;
        this.book = book;
    }

    @Override
    public Scene createScene(Stage stage, ConfigurableApplicationContext context){
        Scene scene = super.createScene(stage, context);
        RentBookController controller = fxmlLoader.getController();
        controller.initialize();
        return scene;
    }

    void setupController(Stage stage, ConfigurableApplicationContext context) {
        RentBookController controller = new RentBookController(primaryStage, stage, context, book);
        fxmlLoader.setController(controller);
    }
}
