package library.proj.gui.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.controllers.BookListController;
import org.springframework.context.ConfigurableApplicationContext;

public class BookListCreator extends SceneCreator {

    public BookListCreator() { super("Book list scene", "/bookListView.fxml"); }

    public Scene createScene(Stage stage, ConfigurableApplicationContext context) {
        Scene scene = super.createScene(stage, context);
        scene.getStylesheets().add(getClass().getResource("/bookListStyles.css").toExternalForm());
        ((BookListController)fxmlLoader.getController()).updateBookList();
        return scene;
    }

    void setupController(Stage stage, ConfigurableApplicationContext context) {
        BookListController controller = new BookListController(stage, context);
        fxmlLoader.setController(controller);
    }
}
