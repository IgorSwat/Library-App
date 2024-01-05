package library.proj.gui.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.controllers.BookDetailsController;
import library.proj.model.Book;
import org.springframework.context.ConfigurableApplicationContext;

public class BookDetailsCreator extends SceneWithNavbarCreator {
    private Book book;
    public BookDetailsCreator(Book book) {
        super("book details scene", "/bookDetailsView.fxml", "/bookDetailsStyles.css");
        this.book=book;
    }

    @Override
    public Scene createScene(Stage stage, ConfigurableApplicationContext context) {
        Scene scene = super.createScene(stage, context);
        BookDetailsController controller = (BookDetailsController)fxmlLoader.getController();
        controller.setupNavbar();
        controller.setFields();
        controller.setupRatingPanel();
        controller.setupBookRating();
        return scene;
    }

    @Override
    void setupController(Stage stage, ConfigurableApplicationContext context) {
        BookDetailsController controller=new BookDetailsController(stage, context, book);
        fxmlLoader.setController(controller);
    }
}
