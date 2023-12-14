package library.proj.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import library.proj.LibraryApplication;
import lombok.Getter;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class LibraryUI extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(LibraryApplication.class).run();
    }

    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage, applicationContext));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    public static class StageReadyEvent extends ApplicationEvent {
        @Getter
        private final ConfigurableApplicationContext context;

        public StageReadyEvent(Stage stage, ConfigurableApplicationContext context) {
            super(stage);
            this.context = context;
        }

        public Stage getStage() {
            return ((Stage) getSource());
        }
    }
}
