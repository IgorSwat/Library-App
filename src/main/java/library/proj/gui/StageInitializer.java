package library.proj.gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<LibraryUI.StageReadyEvent> {
    @Override
    public void onApplicationEvent(LibraryUI.StageReadyEvent event) {
        VBox root = new VBox();
        Stage stage = event.getStage();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
}
