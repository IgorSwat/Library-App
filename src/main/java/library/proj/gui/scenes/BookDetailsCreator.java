package library.proj.gui.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.controllers.BookDetailsController;
import library.proj.gui.controllers.BookListController;
import library.proj.model.Book;
import org.springframework.context.ConfigurableApplicationContext;

public class BookDetailsCreator extends SceneCreator{
    private Book book;
    private SceneCreator previousScene;
    public BookDetailsCreator(Book book, SceneCreator scene) {
        super("book details", "/bookDetailsView.fxml", "/bookDetailsStyles.css");
        this.book=book;
        this.previousScene=scene;
    }

    @Override
    public Scene createScene(Stage stage, ConfigurableApplicationContext context) {
        Scene scene = super.createScene(stage, context);
        BookDetailsController controller = (BookDetailsController)fxmlLoader.getController();
        controller.setFields();
        return scene;
    }

    @Override
    void setupController(Stage stage, ConfigurableApplicationContext context) {
        BookDetailsController controller=new BookDetailsController(stage, context, book, previousScene);
        fxmlLoader.setController(controller);
    }
}
