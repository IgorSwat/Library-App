package library.proj.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import library.proj.LibraryApplication;
import library.proj.gui.events.ChangeSceneEvent;
import library.proj.gui.scenes.LibrarianPanelCreator;
import library.proj.gui.scenes.LoginCreator;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

// Main JavaFX gui application
public class LibraryUI extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(LibraryApplication.class).run();
    }

    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new ChangeSceneEvent(stage, applicationContext, new LoginCreator()));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}
