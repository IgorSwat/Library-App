package library.proj.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.controllers.LoginController;
import library.proj.service.PersonService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<LibraryUI.StageReadyEvent> {
    @Override
    public void onApplicationEvent(LibraryUI.StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginView.fxml"));
            LoginController controller = new LoginController(event.getContext().getBean(PersonService.class));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();

            Stage stage = event.getStage();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Library app");
            stage.show();
        }
        catch (IOException exc) {
            throw new RuntimeException("haha");
        }
    }
}
