package library.proj.gui.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.controllers.MyReservationsController;
import org.springframework.context.ConfigurableApplicationContext;

public class MyReservationsCreator extends SceneWithNavbarCreator {
    public MyReservationsCreator(){
        super("MyReservationsScene", "/myReservationsView.fxml", "/librarianPanelStyles.css");
    }

    @Override
    public Scene createScene(Stage stage, ConfigurableApplicationContext context) {
        Scene scene = super.createScene(stage, context);
        MyReservationsController controller = (MyReservationsController)fxmlLoader.getController();
        controller.setupNavbar();
        controller.setupPagination();
        controller.updateItemsList();
        return scene;
    }

    void setupController(Stage stage, ConfigurableApplicationContext context) {
        MyReservationsController controller = new MyReservationsController(stage, context);
        fxmlLoader.setController(controller);
    }
}
