package library.proj.gui.events;

import javafx.stage.Stage;
import library.proj.gui.scenes.SceneCreatorIf;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class OpenDialogEvent extends ApplicationEvent {
    @Getter
    private final String dialogName;
    @Getter
    private final int dialogWidth;
    @Getter
    private final int dialogHeight;

    @Getter
    private final ConfigurableApplicationContext context;
    @Getter
    private final SceneCreatorIf sceneCreatorIf;

    public OpenDialogEvent(String dialogName, int dialogWidth, int dialogHeight,
                           Stage primaryStage, ConfigurableApplicationContext context, SceneCreatorIf creator) {
        super(primaryStage);
        this.dialogName = dialogName;
        this.dialogWidth = dialogWidth;
        this.dialogHeight = dialogHeight;
        this.context = context;
        this.sceneCreatorIf = creator;
    }

    public Stage getStage() {
        return ((Stage) getSource());
    }
}
