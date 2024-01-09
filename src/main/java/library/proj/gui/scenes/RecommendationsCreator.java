package library.proj.gui.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.controllers.RecommendationsController;
import org.springframework.context.ConfigurableApplicationContext;

public class RecommendationsCreator extends SceneWithNavbarCreator {

    public RecommendationsCreator() { super("Recommendations scene", "/recommendationsView.fxml", "/recommendationsStyles.css"); }

    @Override
    public Scene createScene(Stage stage, ConfigurableApplicationContext context) {
        Scene scene = super.createScene(stage, context);
        RecommendationsController controller = (RecommendationsController)fxmlLoader.getController();
        controller.setupNavbar();
        controller.updateItemsList();
        return scene;
    }

    void setupController(Stage stage, ConfigurableApplicationContext context) {
        RecommendationsController controller = new RecommendationsController(stage, context);
        fxmlLoader.setController(controller);
    }
}
