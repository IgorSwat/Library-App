package library.proj.gui.scenes;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import library.proj.gui.events.OpenDialogEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DialogHandler implements ApplicationListener<OpenDialogEvent> {
    @Override
    public void onApplicationEvent(OpenDialogEvent event) {
        Stage stage = new Stage();
        stage.setWidth(event.getDialogWidth());
        stage.setHeight(event.getDialogHeight());

        Scene scene = event.getSceneCreatorIf().createScene(stage, event.getContext());
        stage.setScene(scene);
        stage.setTitle(event.getDialogName());

        Stage primaryStage = event.getStage();
        primaryStage.hide();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                primaryStage.show();
            }
        });

        stage.show();
    }
}
