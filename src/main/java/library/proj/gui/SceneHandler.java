package library.proj.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import library.proj.gui.events.ChangeSceneEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

// A class responsible for listening to the events and invoking scene creators in appropriate way
@Component
public class SceneHandler implements ApplicationListener<ChangeSceneEvent> {
    @Override
    public void onApplicationEvent(ChangeSceneEvent event) {
        Stage stage = event.getStage();
        Scene scene = event.getSceneCreator().createScene(event.getContext());
        stage.setScene(scene);
        stage.setTitle("Library app");
        stage.show();
    }
}
