package library.proj.gui.events;

import javafx.stage.Stage;
import library.proj.gui.scenes.SceneCreatorIf;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

// Generic event class that is used to pass requests about changing the current scene inside main stage
public class ChangeSceneEvent extends ApplicationEvent {
    @Getter
    private final ConfigurableApplicationContext context;
    @Getter
    private final SceneCreatorIf sceneCreatorIf;

    public ChangeSceneEvent(Stage stage, ConfigurableApplicationContext context, SceneCreatorIf creator) {
        super(stage);
        this.context = context;
        this.sceneCreatorIf = creator;
    }

    public Stage getStage() {
        return ((Stage) getSource());
    }
}
