package library.proj.gui.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.controllers.StatsController;
import org.springframework.context.ConfigurableApplicationContext;

public class StatsCreator extends SceneWithNavbarCreator{
    public StatsCreator() {
        super("Statystyki","/statsView.fxml","/statsStyle.css");
    }

    @Override
    public Scene createScene(Stage stage, ConfigurableApplicationContext context){
        Scene scene=super.createScene(stage,context);
        StatsController controller=(StatsController) fxmlLoader.getController();
        controller.setupNavbar();
        controller.setup();
        return scene;
    }

    @Override
    void setupController(Stage stage, ConfigurableApplicationContext context) {
        StatsController controller=new StatsController(stage,context);
        fxmlLoader.setController(controller);
    }
}
