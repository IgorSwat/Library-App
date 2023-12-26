package library.proj.gui.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.controllers.BookDetailsController;
import library.proj.gui.controllers.MyRentalsController;
import org.springframework.context.ConfigurableApplicationContext;

public class MyRentalsCreator extends SceneCreator{
    public MyRentalsCreator() {
        super("Moje wypożyczenia", "/myRentalsView.fxml", "/myRentalsStyles.css");
    }

    @Override
    public Scene createScene(Stage stage, ConfigurableApplicationContext context) {
        Scene scene = super.createScene(stage, context);
        MyRentalsController controller = (MyRentalsController) fxmlLoader.getController();
        controller.fillContainer();
        return scene;
    }

    @Override
    void setupController(Stage stage, ConfigurableApplicationContext context) {
        MyRentalsController controller=new MyRentalsController(stage, context);
        fxmlLoader.setController(controller);
    }
}
