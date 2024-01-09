package library.proj.gui.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.controllers.LibrarianPanelController;
import org.springframework.context.ConfigurableApplicationContext;

public class LibrarianPanelCreator extends SceneWithNavbarCreator {
    public LibrarianPanelCreator() {
        super("Librarian panel scene", "/librarianPanelView.fxml", "/librarianPanelStyles.css");
    }

    @Override
    public Scene createScene(Stage stage, ConfigurableApplicationContext context) {
        Scene scene = super.createScene(stage, context);
        LibrarianPanelController controller = (LibrarianPanelController)fxmlLoader.getController();
        controller.setupNavbar();
        controller.setupPagination();
        controller.updateItemsList();
        return scene;
    }

    void setupController(Stage stage, ConfigurableApplicationContext context) {
        LibrarianPanelController controller = new LibrarianPanelController(stage, context);
        fxmlLoader.setController(controller);
    }
}
