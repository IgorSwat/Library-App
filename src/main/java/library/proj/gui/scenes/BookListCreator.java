package library.proj.gui.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.controllers.BookListController;
import org.springframework.context.ConfigurableApplicationContext;

public class BookListCreator extends SceneWithNavbarCreator {

    public BookListCreator() { super("Book list scene", "/bookListView.fxml", "/bookListStyles.css"); }

    @Override
    public Scene createScene(Stage stage, ConfigurableApplicationContext context) {
        Scene scene = super.createScene(stage, context);
        BookListController controller = (BookListController)fxmlLoader.getController();
        controller.setupNavbar();
        controller.setupPagination();
        controller.updateItemsList();
        return scene;
    }

    void setupController(Stage stage, ConfigurableApplicationContext context) {
        BookListController controller = new BookListController(stage, context);
        fxmlLoader.setController(controller);
    }
}
